package cloud.tyty.unca.websocket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Response
{
    @Serializable
    data class ActionResponse(
        @SerialName("action") val action: String
    )
    @Serializable
    data class Pong(
        @SerialName("action") val action: String,
        @SerialName("data") val data: UInt
    )
    @Serializable
    data class MessageSend(
        @SerialName ("message") val message: String,
        @SerialName ("action") val action: String,
        )
}
