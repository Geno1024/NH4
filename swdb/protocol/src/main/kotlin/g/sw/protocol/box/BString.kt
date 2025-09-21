package g.sw.protocol.box

import java.io.ByteArrayInputStream
import kotlin.math.ceil
import kotlin.math.log10

data class BString(var str: String = "") : CharSequence, IB<BString>
{
    override fun type(): Char = 's'

    override fun deserialize(bytes: ByteArray): BString = BString.deserialize(bytes)

    override fun serialize(): ByteArray
    {
        val body = str.toByteArray()
        val bodyLength = body.size.toString().toByteArray()
        val lengthOfBodyLength = bodyLength.size.toByte()
        return byteArrayOf(0x73, lengthOfBodyLength) + bodyLength + body
    }

    override fun serSize(): Int = 2 + ceil(log10(length.toDouble())).toInt() + length

    override val length: Int = str.length

    override operator fun get(index: Int): Char = str[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = str.subSequence(startIndex, endIndex)

    companion object
    {
        fun deserialize(bytes: ByteArray): BString
        {
            val lengthOfBodyLength = bytes[1]
            val bodyLength = bytes.copyOfRange(2, 1 + lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bytes.copyOfRange(1 + lengthOfBodyLength.toInt(), 1 + bodyLength).decodeToString()
            return BString(body)
        }

        fun deserialize(bais: ByteArrayInputStream): BString
        {
            bais.skip(1)
            val lengthOfBodyLength = bais.readNBytes(1)[0]
            val bodyLength = bais.readNBytes(lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bais.readNBytes(bodyLength).decodeToString()
            return BString(body)
        }
    }
}
