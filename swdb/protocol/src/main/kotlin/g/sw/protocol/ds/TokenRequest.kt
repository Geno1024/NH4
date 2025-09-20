package g.sw.protocol.ds

import g.sw.protocol.box.BString
import java.util.UUID

data class TokenRequest(
    val username: BString,
    val requestId: BString
) : IDS
{
    companion object
    {
        fun from(username: String): TokenRequest = TokenRequest(
            username = BString(username),
            requestId = BString(UUID.randomUUID().toString())
        )
    }
}
