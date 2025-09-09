package g.sw.protocol.box

data class BStream(var arr: ByteArray) : IB<BStream>
{
    override fun type(): Char = 'b'

    override fun deserialize(byteArray: ByteArray): BStream = BStream.deserialize(byteArray)

    override fun serialize(): ByteArray
    {
        val lengthOfBodyLength = "0010".toByteArray()
        val body = arr
        val bodyLength = body.size.toString().padStart(10, '0').toByteArray()
        return lengthOfBodyLength + bodyLength + body
    }

    companion object
    {
        fun deserialize(arr: ByteArray): BStream
        {
            val lengthOfBodyLength = arr.copyOfRange(0, 4).decodeToString().toInt()
            val bodyLength = arr.copyOfRange(4, 4 + lengthOfBodyLength).decodeToString().toInt()
            val body = arr.copyOfRange(4 + lengthOfBodyLength, 4 + lengthOfBodyLength + bodyLength)
            return BStream(body)
        }
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BStream

        if (!arr.contentEquals(other.arr)) return false

        return true
    }

    override fun hashCode(): Int
    {
        return arr.contentHashCode()
    }
}
