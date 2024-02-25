package cloud.tyty.unca.openMessages

import android.app.Application
import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import cloud.tyty.unca.database.UserDao
import cloud.tyty.unca.mainApp.receivedList
import cloud.tyty.unca.serialization.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import cloud.tyty.unca.database.MessagesViewModel


@Composable
fun MessageLazyColumn(
    viewModel: MessagesViewModel
) {
    val messages = viewModel.messages.collectAsState()

    // Sort the messages by timestamp
        // Update state

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
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (message.isSent) MaterialTheme.colorScheme.secondaryContainer else (MaterialTheme.colorScheme.tertiaryContainer),
                            contentColor = if (message.isSent) MaterialTheme.colorScheme.secondary else (MaterialTheme.colorScheme.tertiary)
                        ),
                    ) {
                        Text(text = message.message, modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}
