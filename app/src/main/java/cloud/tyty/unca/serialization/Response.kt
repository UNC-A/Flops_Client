package cloud.tyty.unca.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Response {
    @Serializable
    data class MessageSend(
        @SerialName("action") val action: String,
        @SerialName("_id") val id: String,
        @SerialName("author") val author: String, // todo user_id
        @SerialName("content") val message: String,
        @SerialName("channel") val channel: String // todo channel
    )

    @Serializable
    data class TypeStatus(
        @SerialName("action") val action: String,
        @SerialName("typing") val typing: Boolean,
        @SerialName("channel") val channel: String, // todo channel
        @SerialName("author") val author: String, // todo user_id
    )

    @Serializable
    data class Pong(
        @SerialName("action") val action: String,
        @SerialName("data") val data: Int
    )
}
