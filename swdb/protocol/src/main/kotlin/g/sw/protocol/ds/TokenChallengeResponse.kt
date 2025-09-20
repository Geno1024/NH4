package g.sw.protocol.ds

import g.sw.protocol.box.BString
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

data class TokenChallengeResponse(
    val requestId: BString,
    val timestamp: BString,
    val response: BString
) : IDS
{
    companion object
    {
        fun fromChallenge(challenge: TokenChallenge, password: String): TokenChallengeResponse = TokenChallengeResponse(
            requestId = challenge.requestId,
            timestamp = challenge.timestamp,
            response = with (Mac.getInstance("HmacSHA256")) {
                init(
                    SecretKeySpec(
                        MessageDigest.getInstance("SHA-256")
                            .digest(password.toByteArray()),
                        "HmacSHA256"
                    )
                )
                BString(doFinal(challenge.nonce.str.toByteArray()).toHexString())
            }
        )

        fun verify(response: TokenChallengeResponse, request: TokenChallenge, username: String, hashedPassword: String): Boolean = response == TokenChallengeResponse(
            requestId = request.requestId,
            timestamp = request.timestamp,
            response = with (Mac.getInstance("HmacSHA256")) {
                init(SecretKeySpec(hashedPassword.hexToByteArray(), "HmacSHA256"))
                BString(doFinal(request.nonce.str.toByteArray()).toHexString())
            }
        )
    }
}
