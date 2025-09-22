package g.sw.protocol.box

import java.io.ByteArrayInputStream

data class BStream(var arr: ByteArray) : IB<BStream>
{
    override fun type(): Char = 'b'

    override fun deserialize(bytes: ByteArray): BStream = BStream.deserialize(bytes)

    override fun serialize(): ByteArray
    {
        val bodyLength = arr.size.toString().toByteArray()
        val lengthOfBodyLength = bodyLength.size.toByte()
        return byteArrayOf(0x62, lengthOfBodyLength) + bodyLength + arr
    }

    override fun serSize(): Int = 2 + arr.size.toString().toByteArray().size + arr.size

    companion object
    {
        fun deserialize(bytes: ByteArray): BStream
        {
            val lengthOfBodyLength = bytes[1]
            val bodyLength = bytes.copyOfRange(2, 1 + lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bytes.copyOfRange(1 + lengthOfBodyLength.toInt(), 1 + bodyLength)
            return BStream(body)
        }

        fun deserialize(bais: ByteArrayInputStream): BStream
        {
            bais.skip(1)
            val lengthOfBodyLength = bais.read()
            val bodyLength = bais.readNBytes(lengthOfBodyLength).decodeToString().toInt()
            val body = bais.readNBytes(bodyLength)
            return BStream(body)
        }
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BStream

        return arr.contentEquals(other.arr)
    }

    override fun hashCode(): Int = arr.contentHashCode()
}
