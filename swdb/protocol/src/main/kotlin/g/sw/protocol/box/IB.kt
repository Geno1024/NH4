package g.sw.protocol.box

interface IB<T> where T : IB<T>
{
    fun type(): Char

    fun deserialize(byteArray: ByteArray): T

    fun serialize(): ByteArray
}
