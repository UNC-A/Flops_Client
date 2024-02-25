package cloud.tyty.unca.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity
data class Messages(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "channel_id") val channelID: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "is_sent") val isSent: Boolean
)

@Dao
interface UserDao
{
    // currently testing phase
    @Insert
    fun insertMessage(vararg messages: Messages)
}
@Database(entities = [Messages::class], version = 1)
abstract class MessagesDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
