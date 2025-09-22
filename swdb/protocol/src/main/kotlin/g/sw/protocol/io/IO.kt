package g.sw.protocol.io

import g.sw.protocol.ds.IDS
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass

interface IO
{
    fun <T : IDS> read(clazz: KClass<T>, input: ByteArray): T
    fun write(ids: IDS): ByteArray

    fun <T : IDS> read(clazz: KClass<T>, input: InputStream): T
    fun write(ids: IDS, output: OutputStream)
}
