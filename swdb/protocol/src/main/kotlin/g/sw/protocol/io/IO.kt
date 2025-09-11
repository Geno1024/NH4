package g.sw.protocol.io

import g.sw.protocol.ds.IDS

interface IO
{
    fun <T : IDS> read(input: ByteArray): T
    fun write(ids: IDS): ByteArray
}
