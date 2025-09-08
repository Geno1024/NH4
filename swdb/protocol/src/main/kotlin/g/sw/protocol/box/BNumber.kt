package g.sw.protocol.box

import java.math.BigDecimal

data class BNumber(var num: BigDecimal) : Number(), IB<BNumber>
{
    override fun toByte(): Byte = num.toByte()

    override fun toDouble(): Double = num.toDouble()

    override fun toFloat(): Float = num.toFloat()

    override fun toInt(): Int = num.toInt()

    override fun toLong(): Long = num.toLong()

    override fun toShort(): Short = num.toShort()

    override fun deserialize(byteArray: ByteArray): BNumber = BNumber.deserialize(byteArray)

    override fun serialize(ib: BNumber): ByteArray
    {
        val lengthOfBodyLength = "0010".toByteArray()
        val body = ib.num.toPlainString().toByteArray()
        val bodyLength = body.size.toString().padStart(10, '0').toByteArray()
        return lengthOfBodyLength + bodyLength + body
    }

    companion object
    {
        fun deserialize(byteArray: ByteArray): BNumber
        {
            val lengthOfBodyLength = byteArray.copyOfRange(0, 4).decodeToString().toInt()
            val bodyLength = byteArray.copyOfRange(4, 4 + lengthOfBodyLength).decodeToString().toInt()
            val body = byteArray.copyOfRange(4 + lengthOfBodyLength, 4 + lengthOfBodyLength + bodyLength).decodeToString().toBigDecimal()
            return BNumber(body)
        }
    }
}
