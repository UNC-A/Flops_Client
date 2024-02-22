package cloud.tyty.unca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.websocket.Action
import cloud.tyty.unca.websocket.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppMessages()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun topAppBarMessages() {
    CenterAlignedTopAppBar(colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,

        ), title = { /* todo username for title*/
        Row(verticalAlignment = Alignment.CenterVertically) {
            // todo implement default icons or similar for Icon()
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(text = "Ash")
        }
    }, navigationIcon = {
        IconButton(onClick = { /* todo change the current page displayed */ }, content = {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = null
            )
        })
    })
}
val sentMessages = mutableStateListOf<Action.MessageSend>()

//@Preview
@Composable
fun openGroupScaffold(webSocketManager: WebSocketManager) {
//    val messages = remember { mutableStateListOf<Action.MessageSend>() }


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
            MessageLazyColumn(sentMessages = sentMessages)
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
