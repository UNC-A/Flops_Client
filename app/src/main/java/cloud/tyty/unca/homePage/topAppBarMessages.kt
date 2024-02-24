package cloud.tyty.unca.homePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
fun TopAppBarRoot() {
    val myViewModel: MyViewModel = viewModel()

    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,

        ), title = { /* todo username for title*/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(text = "Messages")
        }
    }, navigationIcon = {
        IconButton(onClick = { myViewModel.setCurrentPageState(CurrentPage.MESSAGES) }, content = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
            )
        })
    }, actions = {
        Row(verticalAlignment = Alignment.CenterVertically)
        {
            IconButton(onClick = { /*TODO*/ }, content = {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            })
            IconButton(onClick = { /* todo add functionality for searching for users */ }, content = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null
                )
            })
        }


    }

    )
}