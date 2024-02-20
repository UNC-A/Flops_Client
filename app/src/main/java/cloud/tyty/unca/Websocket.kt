package cloud.tyty.unca

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cloud.tyty.unca.websocket.Response
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.*
import io.ktor.http.HttpMethod
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow


const val hostIP: String = "unca.toastxc.xyz"
const val hostPort: Int = 80
const val hostPath: String = "/ws/"

class WebSocketManager {
    private var session: DefaultClientWebSocketSession? = null
    suspend fun connect() {
        val client = HttpClient { install(WebSockets) }
        session =
            client.webSocketSession(HttpMethod.Get, host = hostIP, port = hostPort, path = hostPath)
    }

    suspend fun send(message: String) {
        session?.send(message)
    }

    suspend fun websocketResponse(receivedMessage: MutableList<String>) {
        session?.let { session ->
            try {
                for (frame in session.incoming) {
                    if (frame is Frame.Text) {
                        val action = Gson().fromJson(
                            frame.data.decodeToString(), Response.ActionResponse::class.java
                        )
                        if (action != null)
                        {
                            when (action.action) {
                                "MessageSend" -> {
                                    val messageString = Gson().fromJson(frame.data.decodeToString(), Response.MessageSend::class.java)
                                    receivedMessage.add(messageString.message)
                                }
                                "" -> {}
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // Method to handle reception of messages
}

//endregion

// Decoding from ByteArray to Gson class format <T> represents the enum/class type
inline fun <reified T> gsonDecodeFromByteArray(value: Frame): T =
    Gson().fromJson(value.data.decodeToString(), T::class.java)