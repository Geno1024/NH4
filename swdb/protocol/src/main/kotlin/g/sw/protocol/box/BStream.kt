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
        return byteArrayOf(lengthOfBodyLength) + bodyLength + arr
    }

    override fun serSize(): Int = 1 + arr.size.toString().toByteArray().size + arr.size

    companion object
    {
        fun deserialize(bytes: ByteArray): BStream
        {
            val lengthOfBodyLength = bytes[0]
            val bodyLength = bytes.copyOfRange(1, lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bytes.copyOfRange(lengthOfBodyLength.toInt(), bodyLength)
            return BStream(body)
        }

        fun deserialize(bais: ByteArrayInputStream): BStream
        {
            val lengthOfBodyLength = bais.readNBytes(1)[0]
            val bodyLength = bais.readNBytes(lengthOfBodyLength.toInt()).decodeToString().toInt()
            val body = bais.readNBytes(bodyLength)
            return BStream(body)
        }
    }
}
