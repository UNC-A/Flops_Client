package cloud.tyty.unca.openMessages

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.database.MessageRepository
import cloud.tyty.unca.mainApp.WebSocketManager

@Composable
fun openGroupScaffold(webSocketManager: WebSocketManager, context: Context) {

    Scaffold(topBar = {
        topAppBarMessages()
    }, bottomBar = {
        Alignment.Bottom
        SendMessage(sentMessages, webSocketManager)

    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            MessageLazyColumn(sentMessages = sentMessages, context)
        }
    }
}