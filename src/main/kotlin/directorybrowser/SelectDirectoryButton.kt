package directorybrowser

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SelectDirectoryButton(
    directoryBrowser: DirectoryBrowser = SwingDirectoryChooser(),
    onDirectoryResult: (DirectoryResult) -> Unit,
) {
    Button(onClick = {
        val result = directoryBrowser.showDialog()
        onDirectoryResult(result)
    }) {
        Text("Select Project")
    }
}