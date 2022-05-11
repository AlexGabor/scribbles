package projectInfo

import ProjectInfoState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import directorybrowser.DirectoryResult
import directorybrowser.SelectDirectoryButton
import rememberProjectInfoState
import theme.Body
import theme.Title

@Composable
fun ProjectInfo(
    state: ProjectInfoState = rememberProjectInfoState(),
) {
    Row {
        Column(Modifier.weight(1f)) {
            Title("Project:")
            Body(state.selectedPath ?: "No project selected")

            if (state.selectedPath != null && !state.validProjectPath) {
                Body("Invalid Project Path")
            }
            Spacer(Modifier.height(8.dp))

            if (state.validProjectPath) {
                Title("Package:")
                Body(state.packageName ?: "Could not find package name")
            }
        }
        SelectDirectoryButton(onDirectoryResult = { directoryResult ->
            if (directoryResult is DirectoryResult.Selection) state.selectedPath = directoryResult.path
        })
    }
}