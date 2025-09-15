package g.sw.protocol.box

import java.io.ByteArrayInputStream
import java.math.BigDecimal

data class BNumber(var num: BigDecimal) : Number(), IB<BNumber>
{
    override fun type(): Char = 'n'

    override fun deserialize(bytes: ByteArray): BNumber = BNumber.deserialize(bytes)

    override fun serialize(): ByteArray
    {
        val body = num.toPlainString().toByteArray()
        val bodyLength = body.size.toString().toByteArray()
        val lengthOfBodyLength = bodyLength.size.toByte()
        return byteArrayOf(lengthOfBodyLength) + bodyLength + body
    }

    override fun serSize(): Int = 1 + (num.toPlainString().toByteArray().size.toString().toByteArray().size.toByte()) + num.toPlainString().toByteArray().size

    override fun toByte(): Byte = num.toByte()
    override fun toDouble(): Double = num.toDouble()
    override fun toFloat(): Float = num.toFloat()
    override fun toInt(): Int = num.toInt()
    override fun toLong(): Long = num.toLong()
    override fun toShort(): Short = num.toShort()

    companion object
    {
        fun deserialize(bytes: ByteArray): BNumber
        {
            val lengthOfBodyLength = bytes[0]
            val bodyLength = bytes.copyOfRange(1, lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bytes.copyOfRange(lengthOfBodyLength.toInt(), bodyLength).decodeToString().toBigDecimal()
            return BNumber(body)
        }

        fun deserialize(bais: ByteArrayInputStream): BNumber
        {
            val lengthOfBodyLength = bais.readNBytes(1)[0]
            val bodyLength = bais.readNBytes(lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bais.readNBytes(bodyLength).decodeToString().toBigDecimal()
            return BNumber(body)
        }
    }
}
