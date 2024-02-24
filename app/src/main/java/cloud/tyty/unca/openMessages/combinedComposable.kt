package cloud.tyty.unca.openMessages

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cloud.tyty.unca.WebSocketManager



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





