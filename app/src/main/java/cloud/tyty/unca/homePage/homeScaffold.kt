package cloud.tyty.unca.homePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cloud.tyty.unca.database.MessagesViewModel


@Composable
fun HomeScaffold(messagesViewModel: MessagesViewModel) {

    Scaffold(
        topBar = {
            TopAppBarRoot()
        },
        floatingActionButton = { HomeFAB() },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            lazyListChannels(messagesViewModel)
        }
    }
}