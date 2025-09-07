package g.sw.protocol.ds

import java.security.MessageDigest
import java.time.ZoneOffset
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

data class LoginResponse(
    val username: String,
    val response: String
) : IO
{
    companion object
    {
        fun fromRequest(request: LoginRequest, username: String, password: String) = LoginResponse(
            username = username,
            response = "${request.timestamp.toEpochSecond(ZoneOffset.UTC)}:${with (Mac.getInstance("HmacSHA256")) {
                init(
                    SecretKeySpec(
                        MessageDigest.getInstance("SHA-256")
                            .digest(password.toByteArray()),
                        "HmacSHA256"
                    )
                )
                doFinal(request.nonce.toByteArray())
                    .toHexString()
            }}"
        )

        fun verify(response: LoginResponse, nonce: String, username: String, hashedPassword: String) = response == LoginResponse(
            username = username,
            response = with (response) {
                val (timestamp, _) = this.response.split(":")
                "$timestamp:${with (Mac.getInstance("HmacSHA256")) {
                    init(SecretKeySpec(hashedPassword.hexToByteArray(), "HmacSHA256"))
                    doFinal(nonce.toByteArray()).toHexString()
                }}"
            }
        )
    }
}
