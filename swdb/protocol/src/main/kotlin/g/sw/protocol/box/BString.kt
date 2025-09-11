package g.sw.protocol.box

import java.io.ByteArrayInputStream

data class BString(var str: String = "") : CharSequence, IB<BString>
{
    override fun type(): Char = 's'

    override fun deserialize(byteArray: ByteArray): BString = BString.deserialize(byteArray)

    override fun serialize(): ByteArray
    {
        val lengthOfBodyLength = "0010".toByteArray()
        val body = str.toByteArray()
        val bodyLength = body.size.toString().padStart(10, '0').toByteArray()
        return lengthOfBodyLength + bodyLength + body
    }

    override fun serSize(): Int = 4 + 10 + length

    override val length: Int
        get() = str.length

    override fun get(index: Int): Char = str[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = str.subSequence(startIndex, endIndex)

    companion object
    {
        fun deserialize(byteArray: ByteArray): BString
        {
            val lengthOfBodyLength = byteArray.copyOfRange(0, 4).decodeToString().toInt()
            val bodyLength = byteArray.copyOfRange(4, 4 + lengthOfBodyLength).decodeToString().toInt()
            val body = byteArray.copyOfRange(4 + lengthOfBodyLength, 4 + lengthOfBodyLength + bodyLength).decodeToString()
            return BString(body)
        }

        fun deserialize(bais: ByteArrayInputStream): BString
        {
            val lengthOfBodyLength = bais.readNBytes(4).decodeToString().toInt()
            val bodyLength = bais.readNBytes(lengthOfBodyLength).decodeToString().toInt()
            val body = bais.readNBytes(bodyLength).decodeToString()
            return BString(body)
        }
    }
}
