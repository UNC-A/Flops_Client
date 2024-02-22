package cloud.tyty.unca.homePage

import android.graphics.drawable.Icon
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.timeStampLazyList
import cloud.tyty.unca.timestampConversion

@Preview
@Composable
fun lazyListChannels() {
    addDummyUsers()
    dummyUserList.sortBy { it.timestamp }
    LazyColumn(modifier = Modifier.fillMaxWidth(), content = {
        items(dummyUserList) { users ->
            ListItem(
                leadingContent = {
                    Icon(users.icon, contentDescription = null, modifier = Modifier.size(36.dp))
                },
                headlineContent = { Text(users.name) },
                supportingContent = { Text(users.lastMessage)},
                trailingContent = { Text(timeStampLazyList(users.timestamp))}

            )
        }

    })
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

@Composable
fun cardDesign() {
//    Card()
//    {
//        Column(modifier = Modifier
//            .height(70.dp)
//            .fillMaxWidth()) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 10.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.3f))
//                {
//
//                    Icon(
//                        imageVector = users.icon,
//                        contentDescription = users.name,
//                        modifier = Modifier.size(48.dp)
//                    )
//                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
//                    Text(text = users.name, style = MaterialTheme.typography.labelLarge, maxLines = 2)
//                }
//                Spacer(Modifier.padding(horizontal = 15.dp))
//                Column(modifier = Modifier
//                    .fillMaxWidth(0.8f)
//                    .fillMaxSize()
//                    .padding(vertical = 18.dp))
//                {
//                    Text(text = users.lastMessage, maxLines = 2)
//                }
//                Text(text = timeStampLazyList(users.timestamp), style = MaterialTheme.typography.bodySmall, maxLines = 2)
//            }
//        }
//    }
}