package g.sw.protocol.ds

import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

data class TokenResponse0(
    val username: String,
    val requestTimestampSecond: Long,
    val response: String
) : IO
{
    companion object
    {
        fun fromRequest0(request: TokenRequest0, username: String, password: String) = TokenResponse0(
            username = username,
            requestTimestampSecond = request.timestampSecond,
            response = with (Mac.getInstance("HmacSHA256")) {
                init(
                    SecretKeySpec(
                        MessageDigest.getInstance("SHA-256")
                            .digest(password.toByteArray()),
                        "HmacSHA256"
                    )
                )
                doFinal(request.nonce.toByteArray()).toHexString()
            }
        )

        fun verify(response: TokenResponse0, request: TokenRequest0, username: String, hashedPassword: String) = response == TokenResponse0(
            username = username,
            requestTimestampSecond = request.timestampSecond,
            response = with (Mac.getInstance("HmacSHA256")) {
                init(SecretKeySpec(hashedPassword.hexToByteArray(), "HmacSHA256"))
                doFinal(request.nonce.toByteArray()).toHexString()
            },
        )
    }
}
