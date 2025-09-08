package g.sw.protocol.ds

import g.sw.protocol.box.BNumber
import g.sw.protocol.box.BString
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

data class TokenResponse0(
    val username: BString,
    val requestTimestampSecond: BNumber,
    val response: BString
) : IDS
{
    companion object
    {
        fun fromRequest0(request: TokenRequest0, username: String, password: String) = TokenResponse0(
            username = BString(username),
            requestTimestampSecond = request.timestampSecond,
            response = with (Mac.getInstance("HmacSHA256")) {
                init(
                    SecretKeySpec(
                        MessageDigest.getInstance("SHA-256")
                            .digest(password.toByteArray()),
                        "HmacSHA256"
                    )
                )
                BString(doFinal(request.nonce.str.toByteArray()).toHexString())
            }
        )

        fun verify(response: TokenResponse0, request: TokenRequest0, username: String, hashedPassword: String) = response == TokenResponse0(
            username = BString(username),
            requestTimestampSecond = request.timestampSecond,
            response = with (Mac.getInstance("HmacSHA256")) {
                init(SecretKeySpec(hashedPassword.hexToByteArray(), "HmacSHA256"))
                BString(doFinal(request.nonce.str.toByteArray()).toHexString())
            },
        )
    }
}
