package g.sw.protocol.io

import g.sw.protocol.box.BNumber
import g.sw.protocol.box.BStream
import g.sw.protocol.box.BString
import g.sw.protocol.box.IB
import g.sw.protocol.ds.IDS
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass

class H : IO
{
    override fun <T : IDS> read(clazz: KClass<T>, input: ByteArray): T
    {
        val length = input.sliceArray(0 .. 9).decodeToString().toInt()
        val bais = ByteArrayInputStream(input.sliceArray(10 until (10 + length)))
        var read = 0
        val ids = mutableMapOf<String, IB<*>>()
        while (read < length)
        {
            val lengthOfKeyLength = bais.read()
            val keyLength = bais.readNBytes(lengthOfKeyLength).decodeToString().toInt()
            val key = bais.readNBytes(keyLength).decodeToString()
            val typeOfValue = bais.read()
            val lengthOfValueLength = bais.read()
            val valueLength = bais.readNBytes(lengthOfValueLength).decodeToString().toInt()
            val valueBody = bais.readNBytes(valueLength)
            val value = when (typeOfValue.toChar())
            {
                's' -> BString(valueBody.decodeToString())
                'n' -> BNumber(valueBody.decodeToString().toBigDecimal())
                'b' -> BStream(valueBody)
                else -> TODO()
            }
            ids[key] = value
            read += 3 + lengthOfKeyLength + keyLength + lengthOfValueLength + valueLength
        }
        return IDS.fromMap(clazz, ids)
    }

    override fun write(ids: IDS): ByteArray
    {
        val toWriteList = ids.toMap().map { (key, value) ->
            val keyBody = key.toByteArray()
            val keyBodyLength = keyBody.size.toString().toByteArray()
            byteArrayOf(keyBodyLength.size.toByte()) + keyBodyLength + keyBody + value.serialize()
        }
        val length = toWriteList.sumOf(ByteArray::size)
        val toWrite = ByteArray(10 + length)
        System.arraycopy(length.toString().padStart(10, '0').toByteArray(), 0, toWrite, 0, 10)
        var i = 10
        toWriteList.forEach {
            System.arraycopy(it, 0, toWrite, i, it.size)
            i += it.size
        }
        return toWrite
    }

    override fun <T : IDS> read(clazz: KClass<T>, input: InputStream): T
    {
        val length = input.readNBytes(10).decodeToString().toInt()
        return read(clazz, input.readNBytes(length))
    }

    override fun write(ids: IDS, output: OutputStream)
    {
        val toWriteList = ids.toMap().map { (key, value) ->
            val keyBody = key.toByteArray()
            val keyBodyLength = keyBody.size.toString().toByteArray()
            byteArrayOf(keyBodyLength.size.toByte()) + keyBodyLength + keyBody + value.serialize()
        }
        output.write(toWriteList.sumOf(ByteArray::size).toString().padStart(10, '0').encodeToByteArray())
        toWriteList.forEach(output::write)
    }
}
