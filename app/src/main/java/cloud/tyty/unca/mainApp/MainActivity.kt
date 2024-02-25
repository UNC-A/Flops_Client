package cloud.tyty.unca.mainApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import cloud.tyty.unca.database.MessagesDatabase
import cloud.tyty.unca.database.MessagesViewModel
import cloud.tyty.unca.openMessages.MyAppMessages
import cloud.tyty.unca.homePage.HomeScaffold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainApp : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = MessagesDatabase::class.java,
            name = "database.db"
        ).build()
    }
    private val messagesViewModel by viewModels<MessagesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MessagesViewModel(db.itemDao()) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel = viewModel, messagesViewModel = messagesViewModel)
        }
    }
}

@Composable
fun MyApp(viewModel: MyViewModel, messagesViewModel: MessagesViewModel) {
    val currentPageState by viewModel.currentPageState.collectAsState()
    when (currentPageState) {
        CurrentPage.ROOT -> {
            HomeScaffold()
        }
        CurrentPage.MESSAGES -> {
            MyAppMessages(messagesViewModel)
        }
        CurrentPage.SETTINGS -> {
        }
    }
}
class MyViewModel : ViewModel() {
    private val _currentPageState = MutableStateFlow(CurrentPage.MESSAGES)
    val currentPageState = _currentPageState.asStateFlow()

    fun setCurrentPageState(state: CurrentPage) {
        _currentPageState.value = state
    }
}
enum class CurrentPage {
    ROOT, MESSAGES, SETTINGS
}


