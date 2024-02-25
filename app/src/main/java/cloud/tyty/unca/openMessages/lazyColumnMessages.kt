package cloud.tyty.unca.openMessages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.mainApp.receivedList
import cloud.tyty.unca.serialization.Action

@Composable
fun MessageLazyColumn(
    sentMessages: MutableList<Action.MessageSend>
) {
    val combinedMessageList = mutableListOf<TimeStampedMessages>()


    receivedList.forEach { messages ->
        combinedMessageList.add(
            TimeStampedMessages(
                messages.message, messages.timestamp, false
            )
        )
    }
    sentMessages.forEach { messages ->
        combinedMessageList.add(
            TimeStampedMessages(
                messages.message, messages.timestamp, true
            )
        )
    }
    combinedMessageList.sortBy { it.timestamp }

    LazyColumn() {
        items(combinedMessageList) { message ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (message.sent) Arrangement.End else Arrangement.Start
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = if (message.sent) 110.dp else 20.dp,
                        end = if (message.sent) 20.dp else 110.dp,
                        top = 4.dp
                    )
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (message.sent) MaterialTheme.colorScheme.secondaryContainer else (MaterialTheme.colorScheme.tertiaryContainer),
                            contentColor = if (message.sent) MaterialTheme.colorScheme.secondary else (MaterialTheme.colorScheme.tertiary)
                        ),
                    ) {
                        Text(text = message.messages, modifier = Modifier.padding(10.dp))
                    }
                    // todo display time history for messages
                    // will likely use a .groupBy() to sort as such.
                    // This needs to be done per user - will need new schema for such a task
//                    Text(
//                        text = timestampConversion(message.timestamp),
//                        modifier = Modifier,
//                        fontSize = 8.sp
//                    )
                }
            }
        }
    }
}