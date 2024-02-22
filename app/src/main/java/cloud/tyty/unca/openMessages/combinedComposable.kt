package cloud.tyty.unca.openMessages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cloud.tyty.unca.WebSocketManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppMessages()
        }
    }
}

@Preview
@Composable
fun MyAppMessages() {
    val webSocketManager = WebSocketManager()
    LaunchedEffect(Unit) {
        webSocketManager.connect()
        webSocketManager.webSocketDelegation() // websocket delegation for receiving messages
        // would prefer a separate method for this however, unsure how to approach
    }
    openGroupScaffold(webSocketManager = webSocketManager)
}





