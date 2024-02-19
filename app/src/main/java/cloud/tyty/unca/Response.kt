package cloud.tyty.unca
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Response
{
    @Serializable
    data class Pong(
        @SerialName("action") val action: String,
        @SerialName("data") val data: UInt
    )
    @Serializable
    data class MessageSend(
        @SerialName ("id") val id: String,
        @SerialName ("message") val message: String
    )
}
