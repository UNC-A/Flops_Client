import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


const val hostIP: String = "192.168.1.28"
const val hostPort: Int = 8080


fun main() {
    val client = HttpClient {
        install(WebSockets)
    }
    runBlocking {
        client.ws(method = HttpMethod.Get, host = hostIP, port = hostPort, path = "") {
            val messageOutputRoutine = launch { outputMessages() }
            val userInputRoutine = launch { inputMessages() }

            userInputRoutine.join() // Wait for completion; either "exit" or error
            messageOutputRoutine.cancelAndJoin()
        }
    }
    client.close()
    println("Connection closed. Goodbye!")
}

suspend fun DefaultClientWebSocketSession.inputMessages() {
    while (true) {

        val message = readlnOrNull() ?: ""
        if (message.equals("exit", true)) return
        try {
            // todo update this code - currently used for ping pong
            send(Json.encodeToString(Ping("Ping", (System.currentTimeMillis()).toUInt())))
            delay(200)
        } catch (e: Exception) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }
}

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            message as? Frame.Binary ?: continue

        // Ping pong test response && decode
//            val decode: Pong = jsonDecodeFromByteArray(message)
//            println("Action: ${decode.action} | Data: ${decode.data}")
        }
    } catch (e: Exception) {
        println("Error while receiving: " + e.localizedMessage)
    }
}


//region Ping/Pong data classes
@Serializable
data class Ping(val action: String, val data: UInt)

@Serializable
data class Pong(
    @SerialName("action") val action: String,
    @SerialName("data") val data: UInt
)
//endregion

// Decoding from ByteArray to Json class format <T> represents the enum/class type
inline fun <reified T> jsonDecodeFromByteArray(value: Frame.Binary): T =
    Json.decodeFromString<T>(value.data.decodeToString())