package cloud.tyty.unca.homePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cloud.tyty.unca.database.Message
import cloud.tyty.unca.database.MessagesViewModel
import cloud.tyty.unca.timeStampLazyList

@Composable
fun lazyListChannels(messagesViewModel: MessagesViewModel) {

    val users = messagesViewModel.messages.collectAsState()
    val userValue = users.value
    val uniqueChannels = userValue.groupBy { it.channelID }.values.map { it.last() }
    val userSorted = uniqueChannels.sortedByDescending { it.timestamp }


    messagesViewModel.getAllMessages()


    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        items(userSorted) { users ->

            Spacer(modifier = Modifier.padding(vertical = 1.dp))
            RootCards(users = users)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootCards(users: Message)
{
    ElevatedCard(onClick = {/*todo */})
    {
        Column(modifier = Modifier
            .fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 15.dp))
                {
                    Text(text = users.channelID, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    Text(text = users.message, maxLines = 2)
                }

                Text(text = timeStampLazyList(users.timestamp), style = MaterialTheme.typography.labelSmall, maxLines = 1)
            }
        }
    }
}



