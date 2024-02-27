package cloud.tyty.unca.openMessages

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import cloud.tyty.unca.database.Message
import cloud.tyty.unca.database.MessagesViewModel
import cloud.tyty.unca.mainApp.WebSocketManager
import cloud.tyty.unca.mainApp.isTyping
import cloud.tyty.unca.serialization.Action
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SendMessage(
    webSocketManager: WebSocketManager, viewModel: MessagesViewModel
) {

    var message by remember { mutableStateOf("") }
    var flag by remember { mutableStateOf(false) }
    var typeStatusFlag by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var isTyping by remember { mutableLongStateOf(0) }


    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(value = message,
            onValueChange = {
                message = it
                isTyping = System.currentTimeMillis()
                if (message.isNotEmpty()) {
                    typeStatusFlag = true
                }
                viewModel.viewModelScope.launch {
                    delay(3000)
                    if ((System.currentTimeMillis() - isTyping) > 3000.toLong()) {
                        typeStatusFlag = false
                    }
                }
            },

            Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.medium.copy(CornerSize(10.dp)))
                .onFocusChanged { isFocused = !isFocused },
            maxLines = 3,
            placeholder = { Text(text = "Message") },
            trailingIcon = {
                IconButton(onClick = {
                    flag = true
                    typeStatusFlag = true
                }, content = {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Modifier.padding(10.dp)
                })
            })
    }
    if (typeStatusFlag) {
        LaunchedEffect(Unit) {
            webSocketManager.sendTypeStatus(
                Gson().toJson(
                    Action.TypeStatus(
                        typing = typeStatusFlag, channel = "gfuoghlsduifhuguda"
                    )
                )
            )
        }

    } else {
        LaunchedEffect(Unit) {
            webSocketManager.sendTypeStatus(
                Gson().toJson(
                    Action.TypeStatus(
                        typing = false, channel = "gfuoghlsduifhuguda"
                    )
                )
            )
        }
    }
    if (flag && message.isNotEmpty()) {
        LaunchedEffect(Unit) {
            webSocketManager.send(
                Gson().toJson(
                    Action.MessageSend(
                        content = message,
                        channel = "gfuoghlsduifhuguda"
                    )
                )
            )
            val insertMessage =
                Message(System.currentTimeMillis(), true, "gfuoghlsduifhuguda", message)
            viewModel.insertMessage(insertMessage)

            message = ""
        }
        flag = false
    }

}
