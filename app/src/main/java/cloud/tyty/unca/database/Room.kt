package cloud.tyty.unca.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
    fun getAllMessages(): Flow<List<Message>>}

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessagesDatabase : RoomDatabase() {

    abstract fun itemDao(): UserDao

    companion object {
        @Volatile
        private var Instance: MessagesDatabase? = null

        fun getDatabase(context: Context): MessagesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MessagesDatabase::class.java, "item_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

interface MessagesRepository
{
    fun getAllMessagesStream(): Flow<List<Message>>
    suspend fun insertItem(message: Message)
}
class OfflineItemsRepository(private val userDao: UserDao) : MessagesRepository {
    override fun getAllMessagesStream(): Flow<List<Message>> = userDao.getAllMessages()
        .catch {  } // Handle errors gracefully
        .flowOn(Dispatchers.IO) // Perform database operations on IO thread

    override suspend fun insertItem(message: Message) {
        withContext(Dispatchers.IO) { // Use coroutines for safe threading
            userDao.insertMessage(message)
        }
    }
}

class MessagesViewModel(private val repository: UserDao) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        viewModelScope.launch {
            repository.getAllMessages()
                .collect { messages ->
                    _messages.value = messages
                }
        }
    }

    fun insertMessage(message: Message) = viewModelScope.launch {
        repository.insertMessage(message)
    }
}

