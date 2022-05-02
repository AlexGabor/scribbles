package newservice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import directorybrowser.DirectoryBrowser
import directorybrowser.DirectoryResult
import directorybrowser.SwingDirectoryChooser


@Composable
fun NewServiceScreen(
    state: NewServiceState = rememberScreenState(),
    directoryBrowser: DirectoryBrowser = SwingDirectoryChooser(),
) {
    Column(Modifier.fillMaxSize()) {
        Text(state.selectedPath ?: "Nothing")

        Button(onClick = {
            val result = directoryBrowser.showDialog()
            if (result is DirectoryResult.Selection) {
                state.onPath(result.path)
            }
        }) {
            Text("Select Project")
        }
    }
}