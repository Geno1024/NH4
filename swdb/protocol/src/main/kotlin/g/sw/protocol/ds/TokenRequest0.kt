package g.sw.protocol.ds

import g.sw.protocol.box.BNumber
import g.sw.protocol.box.BString
import java.time.LocalDateTime
import java.time.ZoneOffset

data class TokenRequest0(
    val timestampSecond: BNumber = BNumber(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)),
    val nonce: BString
) : IDS
