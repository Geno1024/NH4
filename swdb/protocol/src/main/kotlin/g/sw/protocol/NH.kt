package g.sw.protocol

import java.net.ServerSocket

class NH
{
    fun start(port: Int = "nh".toByteArray().fold(0) {
            acc, b -> acc * 256 + b
    })
    {
        ServerSocket(port).apply {
            accept().apply {
                val read = { count: Int ->
                    ByteArray(count).apply {
                        inputStream.read(this)
                    }
                }
                val write = { bytes: ByteArray ->
                    outputStream.write(bytes)
                }

                when (read(8))
                {

                }
            }
        }
    }
}
