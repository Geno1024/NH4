package g.sw.protocol.io

import g.sw.protocol.box.BNumber
import g.sw.protocol.box.BStream
import g.sw.protocol.box.BString
import g.sw.protocol.box.IB
import g.sw.protocol.ds.IDS
import java.io.ByteArrayInputStream
import kotlin.reflect.KClass

class H : IO
{
    override fun <T : IDS> read(input: ByteArray): T
    {
        val bais = ByteArrayInputStream(input)
        val clazzLength = bais.readNBytes(10).decodeToString().toInt()
        val clazz = bais.readNBytes(clazzLength).decodeToString()
        val c = Class.forName(clazz)

        val bodyLength = bais.readNBytes(10).decodeToString().toInt()
        val map = mutableMapOf<String, IB<*>>()
        var i = 0
        while (i < bodyLength)
        {
            val type = bais.read().toChar()
            val nameLength = bais.readNBytes(4).decodeToString().toInt()
            val name = bais.readNBytes(nameLength).decodeToString()
            val ent = when (type)
            {
                'n' -> BNumber.deserialize(bais)
                'b' -> BStream.deserialize(bais)
                's' -> BString.deserialize(bais)
                else -> BStream.deserialize(bais)
            }
            map[name] = ent
            val entLength = ent.serSize()
            i += 5 + nameLength + entLength
        }
        return IDS.fromMap(c.kotlin as KClass<T>, map)
    }

    override fun write(ids: IDS): ByteArray
    {
        val clazz = ids.javaClass.name
        val clazzLength = clazz.length.toString().padStart(10, '0')
        val listBody = ids.toMap().entries.map { (key, value) ->
            "${value.type()}${key.length.toString().padStart(4, '0')}$key".toByteArray() + value.serialize()
        }
        val body = ByteArray(listBody.sumOf(ByteArray::size))
        listBody.foldRight(0) { arr, acc ->
            System.arraycopy(arr, 0, body, acc, arr.size)
            arr.size + acc
        }
        val bodyLength = body.size.toString().padStart(10, '0')
        val message = clazzLength + clazz + bodyLength
        return message.toByteArray() + body
    }
}
