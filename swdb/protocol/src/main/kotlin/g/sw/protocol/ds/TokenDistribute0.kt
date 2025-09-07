package g.sw.protocol.ds

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

data class TokenDistribute0(
    val username: String,
    val token: String,
    val requestTimestampSecond: Long,
    val validBefore: Long
) : IO
{
    companion object
    {
        fun fromResponse0(response0: TokenResponse0, expireSecond: Long) = TokenDistribute0(
            username = response0.username,
            token = with (Mac.getInstance("HmacSHA256")) {
                init(SecretKeySpec(response0.response.toByteArray(), "HmacSHA256"))
                doFinal("${response0.requestTimestampSecond}:${response0.requestTimestampSecond + expireSecond}".toByteArray()).toHexString()
            },
            requestTimestampSecond = response0.requestTimestampSecond,
            validBefore = response0.requestTimestampSecond + expireSecond
        )
    }
}
