package cloud.tyty.unca.openMessages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.database.Message
import cloud.tyty.unca.database.MessagesViewModel
import cloud.tyty.unca.mainApp.WebSocketManager
import cloud.tyty.unca.serialization.Action
import com.google.gson.Gson


// used to group and sort messages depending on sent and received


// This calls the /websocket/Action.MessageSend data class for storing message data
val sentMessages = mutableStateListOf<Action.MessageSend>()


@Composable
fun SendMessage(
    webSocketManager: WebSocketManager,
    viewModel: MessagesViewModel
) {


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
    if (flag && message.isNotEmpty()) {
        LaunchedEffect(Unit) {
            webSocketManager.send(
                Gson().toJson(Action.MessageSend(message, (System.currentTimeMillis())))
            )
            val insertMessage = Message(System.currentTimeMillis(), true, "channel1", message)
            viewModel.insertMessage(insertMessage)

            message = ""
        }
        flag = false
    }
}
