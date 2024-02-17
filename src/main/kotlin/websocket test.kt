import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

const val hostIP: String = "192.168.121.15"
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
            send(message)
            delay(200)
        } catch (e: Exception) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }
}

suspend fun output(): String {
    while (true) {
        delay(200)
        return Random().nextLong(0, 999999999999999999).toString()
    }
}

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            println(message.readText())
        }
    } catch (e: Exception) {
        println("Error while receiving: " + e.localizedMessage)
    }
    
}
// todo json serialisation
//
//@Serializable
//data class Ping(val action: String, val data: Int?)
//
//fun test(): String {
//    return Json.encodeToString(Ping("Ping", 69))
//}




