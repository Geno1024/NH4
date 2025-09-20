package g.sw.protocol.ds

import g.sw.protocol.box.BString
import java.security.SecureRandom

data class TokenResponse(
    val username: BString,
    val token: BString
) : IDS
{
    companion object
    {
        fun generate(request: TokenRequest, challenge: TokenChallenge): TokenResponse = TokenResponse(
            username = request.username,
            token = with (ByteArray(32) { 0 }) {
                SecureRandom().nextBytes(this)
                BString(challenge.timestamp.str + "," + toHexString())
            }
        )
    }
}
