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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.database.Message
import cloud.tyty.unca.database.MessagesViewModel


@Composable
fun MessageLazyColumn(
    viewModel: MessagesViewModel
) {
    val messages = viewModel.messages.collectAsState()

    LazyColumn {
        items(messages.value) { message ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (message.isSent) Arrangement.End else Arrangement.Start
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = if (message.isSent) 110.dp else 20.dp,
                        end = if (message.isSent) 20.dp else 110.dp,
                        top = 4.dp
                    )
                ) {
                    CardDisplayMessage(message = message)
                }
            }
        }
    }
}
@Composable
fun CardDisplayMessage(message: Message)
{
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (message.isSent) MaterialTheme.colorScheme.secondaryContainer else (MaterialTheme.colorScheme.tertiaryContainer),
            contentColor = if (message.isSent) MaterialTheme.colorScheme.secondary else (MaterialTheme.colorScheme.tertiary)
        ),
    ) {
        Text(text = message.message, modifier = Modifier.padding(10.dp))
    }
}