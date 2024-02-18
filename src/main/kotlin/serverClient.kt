import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pong(
    @SerialName("action") val action: String,
    @SerialName("data") val data: UInt
)