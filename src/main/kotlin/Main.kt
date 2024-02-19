import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.serializer


class WebSocketManager() {
    private var session: DefaultClientWebSocketSession? = null
    suspend fun connect() {
        val client = HttpClient { install(WebSockets) }
        session = client.webSocketSession(HttpMethod.Get, host = hostIP, port = hostPort, path = "")
    }

    suspend fun send(message: String) {
        session?.send(message)
    }
}

// Composable function to display UI
@Preview
@Composable
fun SendMessage() {
    val webSocketManager = remember { WebSocketManager() }
    var message by remember { mutableStateOf("") }
    var flag by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        webSocketManager.connect()
    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            Modifier
                .weight(1f) // TextField takes up all the available space
                .padding(horizontal = 8.dp)
                .clip(shape = CircleShape)
                .height(50.dp),
            placeholder = { Text(text = "Message") }
        )
        Button(onClick = {
            flag = true
        }, Modifier
            .padding(horizontal = 8.dp)
            .width(144.dp)) {
            Text(text = "Send")
        }
    }
    if (flag) {
        LaunchedEffect(Unit) {
            webSocketManager.send(
                Gson().toJson(Action.MessageSend(message))
            )
            message = ""
        }
        flag = false
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    Box(Modifier.fillMaxWidth().fillMaxHeight())
    {

        Scaffold(bottomBar = { SendMessage() }) {
            Modifier.padding(50.dp)
        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
