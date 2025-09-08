package g.sw.protocol.box

data class BString(var str: String = "") : CharSequence, IB<BString>
{
    override fun deserialize(byteArray: ByteArray): BString = BString.deserialize(byteArray)

    override fun serialize(ib: BString): ByteArray
    {
        val lengthOfBodyLength = "0010".toByteArray()
        val body = ib.str.toByteArray()
        val bodyLength = body.size.toString().padStart(10, '0').toByteArray()
        return lengthOfBodyLength + bodyLength + body
    }

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
    }
}
