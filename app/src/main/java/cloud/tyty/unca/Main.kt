package cloud.tyty.unca

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*


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

// Composable function for Sending Messages
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
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(value = message,
            onValueChange = { message = it },
            Modifier
                .weight(1f) // TextField takes up all the available space
                .clip(MaterialTheme.shapes.medium.copy(CornerSize(10.dp))),
            placeholder = { Text(text = "Message") },
            trailingIcon = {
                IconButton(onClick = { flag = true }, content = {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Modifier.padding(10.dp)
                })
            })
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

@Preview
@Composable
fun SentMessageCard() {
    // todo cards
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun topAppBarMessages() {
    CenterAlignedTopAppBar(colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,
    ),

        title = { /* todo username for title*/
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


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun openGroupScaffold() {
    var presses by remember { mutableIntStateOf(0) }

    Scaffold(topBar = {
        topAppBarMessages()
    }, bottomBar = {
        Alignment.Bottom
        SendMessage()

    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "")

        }
    }
}

