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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.timeStampLazyList

@Preview
@Composable
fun lazyListChannels() {
    addDummyUsers()
    dummyUserList.sortBy { it.timestamp }

    LazyColumn(modifier = Modifier.fillMaxWidth(), content = {
        items(dummyUserList) { users ->
            Spacer(modifier = Modifier.padding(vertical = 1.dp))
            RootCards(users = users)
        }
    })
}

@Composable
fun RootCards(users: dummyUsers)
{
    Card()
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
                    Text(text = users.name, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                    Text(text = users.lastMessage, maxLines = 2)
                }
                Text(text = timeStampLazyList(users.timestamp), style = MaterialTheme.typography.labelSmall, maxLines = 1)
            }
        }
    }
}

data class dummyUsers(
    val name: String, val timestamp: Long, val lastMessage: String, val icon: ImageVector
)
val dummyUserList = mutableListOf<dummyUsers>()
fun addDummyUsers() {
    dummyUserList.add(
        dummyUsers(
            "Kaia",
            timestamp = 1708576972,
            lastMessage = "How are you doing today? Remember, we have dinner tonight at 6!",
            icon = Icons.Default.AccountCircle
        )
    )
    dummyUserList.add(
        dummyUsers(
            "Ashy",
            1708579723,
            "Hello, I am just writing this to test the effectiveness of the lazyColumn",
            Icons.Default.AccountCircle
        )
    )
}



