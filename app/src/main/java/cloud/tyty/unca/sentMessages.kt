package cloud.tyty.unca

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.websocket.Action
import cloud.tyty.unca.websocket.Response
import com.google.gson.Gson
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.JdkConstants
import java.sql.Timestamp

data class TimeStampedMessages(val messages: String, val timestamp: Long)
@Composable
fun MessageLazyColumn(
    sentMessages: MutableList<Action.MessageSend>
) {
    val combinedMessageList = mutableListOf<TimeStampedMessages>()

    receivedList.forEach { messages ->
        combinedMessageList.add(TimeStampedMessages(messages.message, messages.timestamp))
    }
    sentMessages.forEach { messages ->
        combinedMessageList.add(TimeStampedMessages(messages.message, messages.timestamp))
    }



    combinedMessageList.sortBy { it.timestamp }

    LazyColumn() {
        items(combinedMessageList) { message ->
            Text(text = message.messages, modifier = Modifier.padding(10.dp))
        }
    }
}

// Composable function for Sending Messages
@Composable
fun SendMessage(sentMessages: MutableList<Action.MessageSend>, webSocketManager: WebSocketManager) {
    var message by remember { mutableStateOf("") }
    var flag by remember { mutableStateOf(false) }

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
                Gson().toJson(Action.MessageSend(message, 0))
            )
            sentMessages.add(Action.MessageSend(message, 0))
            message = ""
        }
        flag = false
    }
}