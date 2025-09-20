package g.sw.protocol.ds

import g.sw.protocol.box.BString
import java.security.SecureRandom
import java.time.LocalDateTime

data class TokenChallenge(
    val requestId: BString,
    val timestamp: BString,
    val nonce: BString
) : IDS
{
    companion object
    {
        fun from(request: TokenRequest): TokenChallenge = TokenChallenge(
            requestId = request.requestId,
            timestamp = BString(LocalDateTime.now().toString()),
            nonce = with (ByteArray(32) { 0 }) {
                SecureRandom().nextBytes(this)
                BString(toHexString())
            },
        )
    }
}
