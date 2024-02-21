package cloud.tyty.unca

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cloud.tyty.unca.websocket.Response
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.*
import io.ktor.http.HttpMethod
import io.ktor.websocket.*


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
    // sends a basic message request to the websocket server // will need user id associated with it on send
    suspend fun send(message: String) {
        session?.send(message)
    }

//     Method to handle reception of messages
    private suspend fun webSocketResponse(): com.google.gson.JsonObject? {
        session?.let { session ->
            for (frame in session.incoming) {
                if (frame is Frame.Text) {
                    return Gson().fromJson(
                        frame.data.decodeToString(), com.google.gson.JsonObject::class.java
                    )
                }
            }
        }
        return null
    }
    suspend fun webSocketDelegation() {
        while(true)
        {
            val webSocketResponse = webSocketResponse()
            if (webSocketResponse != null) {
                when (webSocketResponse["action"].asString) {
                    "MessageSend" -> {
                        val newMessage = Response.MessageSend(
                            message = webSocketResponse["message"].asString,
                            action = webSocketResponse["action"].asString
                        )
                        // Add the new message to the list
                        messageList += newMessage
                    }
                }
            }
        }

    }
}
val messageList by mutableStateOf(mutableStateListOf<Response.MessageSend>())


//suspend fun main() {
//    val webSocketManager = WebSocketManager()
//    while (true) {
//        webSocketManager.webSocketDelegation()
//    }
//}



//endregion

// Decoding from ByteArray to Gson class format <T> represents the enum/class type
inline fun <reified T> gsonDecodeFromByteArray(value: Frame): T =
    Gson().fromJson(value.data.decodeToString(), T::class.java)