package cloud.tyty.unca.openMessages

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import cloud.tyty.unca.mainApp.WebSocketManager




@Composable
fun MyAppMessages(context: Context) {
    val webSocketManager = WebSocketManager()
    LaunchedEffect(Unit) {
        webSocketManager.connect()
        webSocketManager.webSocketDelegation(context) // websocket delegation for receiving messages
        // would prefer a separate method for this however, unsure how to approach
    }
    openGroupScaffold(webSocketManager = webSocketManager, context)
}





