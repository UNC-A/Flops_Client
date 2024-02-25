package cloud.tyty.unca.database

import android.content.Context
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.platform.LocalContext
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Entity
data class Messages(
    @PrimaryKey val timestamp: Long,
    @ColumnInfo(name = "is_sent") val isSent: Boolean,
    @ColumnInfo(name = "channel_id") val channelID: String,
    @ColumnInfo(name = "message") val message: String
)

@Dao
interface UserDao {
    // currently testing phase
    @Insert
    fun insertMessage(vararg messages: Messages)

    @Query("SELECT * FROM messages")
    fun getAll(): List<Messages>
}

@Database(entities = [Messages::class], version = 1)
abstract class MessagesDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

class MessageRepository(private val userDao: UserDao) {
    fun insertMessage(message: Messages) {
        userDao.insertMessage(message)
    }

    fun getAll(): List<Messages> {
        return userDao.getAll()
    }
}


fun addMessageSent(context: Context, message: String) {
    CoroutineScope(Dispatchers.IO).launch {

        val db = Room.databaseBuilder(
            context, MessagesDatabase::class.java, "MessagesDatabase"
        ).build()
        val userDao = db.userDao()

        userDao.insertMessage(
            Messages(
                System.currentTimeMillis(), true, "channel1", message
            )
        )
    }
}

fun addMessageReceived(context: Context, message: String) {
    CoroutineScope(Dispatchers.IO).launch {
        val db = Room.databaseBuilder(
            context, MessagesDatabase::class.java, "MessagesDatabase"
        ).build()
        val userDao = db.userDao()

        userDao.insertMessage(
            Messages(
                System.currentTimeMillis(), false, "channel1", message
            )
        )
    }
}
