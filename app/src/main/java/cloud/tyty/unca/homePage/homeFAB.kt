package cloud.tyty.unca.homePage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun HomeFAB() {
    FloatingActionButton(onClick = { /*TODO*/ },
        content = { Icon(imageVector = Icons.Default.Create, contentDescription = "Message a new contact")})
}