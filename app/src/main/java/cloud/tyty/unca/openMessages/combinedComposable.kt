package cloud.tyty.unca.openMessages

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.database.MessagesViewModel
import cloud.tyty.unca.mainApp.MyViewModel
import cloud.tyty.unca.mainApp.WebSocketManager



@Composable
fun MyAppMessages(viewModel: MessagesViewModel) {
    val webSocketManager = WebSocketManager()
    LaunchedEffect(Unit) {
        webSocketManager.connect()
        webSocketManager.webSocketDelegation(viewModel) // websocket delegation for receiving messages
        // would prefer a separate method for this however, unsure how to approach
    }
    Scaffold(topBar = {
        topAppBarMessages()
    }, bottomBar = {
        Alignment.Bottom
        SendMessage(webSocketManager, viewModel)

    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            MessageLazyColumn(viewModel)
        }
    }
}





