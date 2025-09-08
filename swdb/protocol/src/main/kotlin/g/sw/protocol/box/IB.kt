package g.sw.protocol.box

interface IB<T> where T : IB<T>
{
    fun deserialize(byteArray: ByteArray): T

    fun serialize(ib: T): ByteArray
}
