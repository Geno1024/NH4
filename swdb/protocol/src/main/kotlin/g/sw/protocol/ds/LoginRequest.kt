package g.sw.protocol.ds

import java.time.LocalDateTime

data class LoginRequest(
    val timestamp: LocalDateTime,
    val nonce: String
) : IO
