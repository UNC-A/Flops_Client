import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch


const val hostIP: String = "192.168.1.28"
const val hostPort: Int = 8080
const val hostPath: String = "?session=r7efw534543d0fhptrh"

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val gson = Gson()
//         Ping pong test response && decode
            val decode: Response.Pong = gsonDecodeFromByteArray(message)
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