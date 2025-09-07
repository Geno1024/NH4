package g.sw.protocol.ds

import java.time.LocalDateTime
import java.time.ZoneOffset

data class TokenRequest0(
    val timestampSecond: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    val nonce: String
) : IO
