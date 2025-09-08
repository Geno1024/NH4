package g.sw.protocol.ds

import g.sw.protocol.box.BNumber
import g.sw.protocol.box.BString
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

data class TokenDistribute0(
    val username: BString,
    val token: BString,
    val requestTimestampSecond: BNumber,
    val validBefore: BNumber
) : IDS
{
    companion object
    {
        fun fromResponse0(response0: TokenResponse0, expireSecond: Long) = TokenDistribute0(
            username = response0.username,
            token = with (Mac.getInstance("HmacSHA256")) {
                init(SecretKeySpec(response0.response.str.toByteArray(), "HmacSHA256"))
                BString(doFinal("${response0.requestTimestampSecond}:${response0.requestTimestampSecond.num.toLong() + expireSecond}".toByteArray()).toHexString())
            },
            requestTimestampSecond = response0.requestTimestampSecond,
            validBefore = BNumber(response0.requestTimestampSecond.num.toLong() + expireSecond)
        )
    }
}
