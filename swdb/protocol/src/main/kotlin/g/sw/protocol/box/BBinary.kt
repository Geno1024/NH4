package g.sw.protocol.box

import java.io.ByteArrayInputStream

data class BBinary(var bin: Boolean = false) : IB<BBinary>
{
    override fun type(): Char = '2'

    override fun deserialize(bytes: ByteArray): BBinary = BBinary.deserialize(bytes)

    override fun serialize(): ByteArray = byteArrayOf(
        0x32,
        1,
        1,
        (if (bin) 'T' else 'F').code.toByte()
    )

    override fun serSize(): Int = 4

    companion object
    {
        fun deserialize(bytes: ByteArray): BBinary = BBinary(bytes[3] == 0x54.toByte())

        fun deserialize(bais: ByteArrayInputStream): BBinary = BBinary(with (bais) { skip(3); read() == 0x54 })
    }
}
