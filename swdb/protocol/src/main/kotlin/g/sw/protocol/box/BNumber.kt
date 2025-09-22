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
        return byteArrayOf(0x6e, lengthOfBodyLength) + bodyLength + body
    }

    override fun serSize(): Int = 2 + (num.toPlainString().toByteArray().size.toString().toByteArray().size.toByte()) + num.toPlainString().toByteArray().size

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
            val lengthOfBodyLength = bytes[1]
            val bodyLength = bytes.copyOfRange(2, 1 + lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bytes.copyOfRange(1 + lengthOfBodyLength.toInt(), 1 + bodyLength).decodeToString().toBigDecimal()
            return BNumber(body)
        }

        fun deserialize(bais: ByteArrayInputStream): BNumber
        {
            bais.skip(1)
            val lengthOfBodyLength = bais.read()
            val bodyLength = bais.readNBytes(lengthOfBodyLength).decodeToString().toInt()
            val body = bais.readNBytes(bodyLength).decodeToString().toBigDecimal()
            return BNumber(body)
        }
    }
}
