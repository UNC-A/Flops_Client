package cloud.tyty.unca.serialization
import kotlinx.serialization.Serializable


// UNCA OPEN SPEC DOCUMENT
// https://github.com/UNC-A/spec/blob/main/websocket/actions.md
sealed class Action {
    @Serializable
    data class MessageSend(
        val content: String,
        val channel: String, // todo channel id
        val action: String = "MessageSend"

        )

    // note: if typing == true in more than one channels; the oldest will be removed
    @Serializable
    data class TypeStatus(
        val typing: Boolean,
        val channel: String, // todo channel id,
        val action: String = "TypeStatus"

        )

    @Serializable
    data class Ping(
        val data: Int = 0,
        val action: String = "Ping"

        )
}
