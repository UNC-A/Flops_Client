package cloud.tyty.unca.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val timestamp: Long,
    @ColumnInfo(name = "is_sent") val isSent: Boolean,
    @ColumnInfo(name = "channel_id") val channelID: String,
    @ColumnInfo(name = "message") val message: String
)

@Dao
interface UserDao {
    @Insert
    suspend fun insertMessage(message: Message)

    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<Message>>
}


@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessagesDatabase : RoomDatabase() {

    abstract fun itemDao(): UserDao
}

class MessagesViewModel(private val repository: UserDao) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        viewModelScope.launch {
            repository.getAllMessages().collect { messages ->
                _messages.value = messages
            }
        }
    }

    fun insertMessage(message: Message) = viewModelScope.launch {
        repository.insertMessage(message)
    }

    // Refreshes the messages list
    fun getAllMessages() = viewModelScope.launch {
        val messages = repository.getAllMessages()
        _messages.value = messages
    }
}

