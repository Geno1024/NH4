package g.sw.protocol.ds

import g.sw.protocol.box.BBinary
import g.sw.protocol.box.BString
import java.security.SecureRandom

data class TokenResponse(
    val verified: BBinary,
    val username: BString,
    val token: BString
) : IDS
{
    companion object
    {
        fun generate(verified: Boolean, request: TokenRequest, challenge: TokenChallenge): TokenResponse = TokenResponse(
            verified = BBinary(verified),
            username = request.username,
            token = if (verified)
            {
                with(ByteArray(32)) {
                    SecureRandom().nextBytes(this)
                    BString(challenge.timestamp.str + "," + toHexString())
                }
            } else
            {
                BString()
            }
        )
    }
}
