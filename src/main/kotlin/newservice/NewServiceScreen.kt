package newservice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewServiceScreen(
    state: NewServiceState = rememberScreenState()
) {
    Column(Modifier.fillMaxSize()) {
        Text(state.selectedPath ?: "Nothing")

        Button(onClick = {  }) {
            Text("Select Project")
        }
    }
}