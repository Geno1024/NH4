package g.sw.protocol.box

interface IB<T> where T : IB<T>
{
    fun type(): Char

    fun deserialize(bytes: ByteArray): T

    fun serialize(): ByteArray

    fun serSize(): Int
}
