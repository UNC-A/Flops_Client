package cloud.tyty.unca.openMessages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cloud.tyty.unca.mainApp.CurrentPage
import cloud.tyty.unca.mainApp.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun topAppBarMessages() {
    val myViewModel: MyViewModel = viewModel()
    CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,

        ), title = { /* todo username for title*/
        Row(verticalAlignment = Alignment.CenterVertically) {
            // todo implement default icons or similar for Icon()
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(text = "Ash")
        }
    }, navigationIcon = {
        IconButton(onClick = {
            myViewModel.setCurrentPageState(CurrentPage.ROOT)
        }, content = {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = null
            )
        })
    })
}

