package cloud.tyty.unca.mainApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cloud.tyty.unca.openMessages.MyAppMessages
import cloud.tyty.unca.homePage.HomeScaffold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

            setContent {

                MyApp(viewModel = viewModel)
            }
        }
}

@Composable
fun MyApp(viewModel: MyViewModel) {
    val currentPageState by viewModel.currentPageState.collectAsState()
    when (currentPageState) {
        CurrentPage.ROOT -> {
            HomeScaffold()
        }
        CurrentPage.MESSAGES -> {
            MyAppMessages()
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
enum class CurrentPage() {
    ROOT, MESSAGES, SETTINGS
}