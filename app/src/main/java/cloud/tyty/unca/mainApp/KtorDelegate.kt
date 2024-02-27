package cloud.tyty.unca.mainApp

import cloud.tyty.unca.database.Message
import cloud.tyty.unca.database.MessagesViewModel
import cloud.tyty.unca.serialization.Response
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.*
import io.ktor.http.HttpMethod
import io.ktor.websocket.*

// wss://unca.toastxc.xyz/v1/ws/?session=fdaihbfdsuhdsa
//const val host: String = "unca.toastxc.xyz/v0/ws/?session=fdaihbfdsuhdsa"
const val hostIP: String = "unca.toastxc.xyz"
const val hostPort: Int = 80
const val hostPath: String = "/v1/ws/?session=fdaihbfdsuhdsa"

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
    suspend fun sendTypeStatus(typeStatus: String) {
        session?.send(typeStatus)
    }
    private suspend fun webSocketResponse(): JsonObject? {
        session?.let { session ->
            for (frame in session.incoming) {
                if (frame is Frame.Text) {
                    return Gson().fromJson(
                        frame.data.decodeToString(), JsonObject::class.java
                    )
                }
            }
        }
        return null
    }
    suspend fun webSocketDelegation(
        viewModel: MessagesViewModel
    ) {
        while(true)
        {
            val webSocketResponse = webSocketResponse()
            if (webSocketResponse != null) {
                when (webSocketResponse["action"].asString) {
                    "MessageSend" -> {
                        val content = webSocketResponse["content"].asString
                        val channel = webSocketResponse[""]

                        // Add the new message to the list
                        val receivedMessages = Message(System.currentTimeMillis(), false, "channel1", content)
                        viewModel.insertMessage(receivedMessages)
                    }
                    "TypeStatus" -> {
                        val typing = webSocketResponse["typing"].asBoolean
                        val channel = webSocketResponse["channel"].asString
                        val author = webSocketResponse["author"].asString

                        val userTypeStatus = Response.TypeStatus("TypeStatus", typing, channel, author)

                        isTyping.add(userTypeStatus)
                    }
                }
            }
        }
    }
}
val isTyping = mutableListOf<Response.TypeStatus>()
