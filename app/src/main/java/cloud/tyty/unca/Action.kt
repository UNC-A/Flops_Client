package cloud.tyty.unca
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable





// UNCA OPEN SPEC DOCUMENT
// https://github.com/UNC-A/spec/blob/main/websocket/actions.md
sealed class Action {
    // region Establish/Ping
    @Serializable
    data class ManualEstablish(
        val data: Int?,
        private val action: String = "ManualEstablish"
    )

    @Serializable
    data class Ping(
        val data: Int,
        private val action: String = "Ping"
    )

    //endregion
    // region Message Send/Edit/Delete/TypeStatus
    @Serializable
    data class MessageSend(
        val message: String,
        val action: String = "MessageSend"
    )

    data class MessageEdit(
        val messageFields: String,
        val messageRemove: String,
        val action: String = "MessageEdit"
    )

    @Serializable
    data class MessageDelete(
        val messageId: UInt,
        val action: String = "MessageDelete"
    )

    @Serializable
    data class TypeStatus(
        val typing: Boolean,
        val channel: String,
        val action: String = "TypeStatus"
    )

    //endregion
    // region Friend Add/Remove | Block Add/Remove
    @Serializable
    data class FriendAdd(
        val user: String?,
        val username: String?,
        val action: String = "FriendAdd"
    )

    @Serializable
    data class FriendRemove(
        val user: String?,
        val username: String?,
        val action: String =  "FriendRemove"
    )

    @Serializable
    data class BlockAdd(
        val user: String,
        val action: String = "BlockAdd"
    )

    @Serializable
    data class BlockRemove(
        val user: String,
        val action: String = "BlockRemove"
    )

    // endregion
    // region Channel Create/Remove
    @Serializable
    data class ChannelCreate(
        val user: String,
        val action: String = "ChannelCreate"
    )

    @Serializable
    data class ChannelRemove(
        val user: String,
        val action: String = "ChannelRemove")
    //endregion
}
