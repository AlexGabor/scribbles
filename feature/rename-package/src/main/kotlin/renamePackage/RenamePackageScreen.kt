import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import projectInfo.ProjectInfo
import renamePackage.RenamePackageState
import renamePackage.rememberRenamePackageState

@Composable
fun RenamePackageScreen(
    state: RenamePackageState = rememberRenamePackageState()
) {
    val projectInfoState = rememberProjectInfoState().apply {
        state.selectedPath = this.selectedPath
        state.packageName = this.packageName
    }

    LaunchedEffect(Unit) { projectInfoState.selectedPath = state.selectedPath }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { ProjectInfo(projectInfoState) }
        item { RenameFields(state) }
    }
}

@Composable
fun RenameFields(
    state: RenamePackageState,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextField(state.newPackageName, { state.newPackageName = it })
        Button(onClick = { state.onRename() }, enabled = state.isValidPackageName) {
            Text("Rename")
        }
    }
}
