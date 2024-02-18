import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.util.Identity.encode
import io.ktor.utils.io.core.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlin.text.toByteArray


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
            send(Gson().toJson(Action.Ping(69)))



              } catch (e: Exception) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }
}

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val gson = Gson()
//         Ping pong test response && decode
            val decode: Pong = gsonDecodeFromByteArray(message)
//            val decode: Pong = gson.fromJson(message, Pong::class.java)

            println("Action: ${decode.action} | Data: ${decode.data}")
        }
    } catch (e: Exception) {
        println("Error while receiving: " + e.localizedMessage)
    }
}




//endregion

// Decoding from ByteArray to Gson class format <T> represents the enum/class type
inline fun <reified T> gsonDecodeFromByteArray(value: Frame): T =
    Gson().fromJson(value.data.decodeToString(), T::class.java)