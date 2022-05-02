package newservice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import directorybrowser.DirectoryResult
import directorybrowser.SelectDirectoryButton


@Composable
fun NewServiceScreen(
    state: NewServiceState = rememberScreenState(),
) {
    Column(Modifier.fillMaxSize()) {
        Text(state.selectedPath ?: "Nothing")
        SelectDirectoryButton(onDirectoryResult = { directoryResult ->
            if (directoryResult is DirectoryResult.Selection) state.onPath(directoryResult.path)
        })

        if (state.selectedPath != null && !state.validProjectPath) {
            Text("Invalid Project Path")
        }

        if (state.validProjectPath) {
            Text(state.packageName.toString())
        }
    }
}