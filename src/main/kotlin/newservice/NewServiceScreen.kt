package newservice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import directorybrowser.DirectoryResult
import directorybrowser.SelectDirectoryButton


@Composable
fun NewServiceScreen(
    state: NewServiceState = rememberNewServiceScreenState(),
) {
    Column(Modifier.fillMaxSize()) {
        Text(state.selectedPath ?: "No project selected")
        SelectDirectoryButton(onDirectoryResult = { directoryResult ->
            if (directoryResult is DirectoryResult.Selection) state.onPath(directoryResult.path)
        })

        if (state.selectedPath != null && !state.validProjectPath) {
            Text("Invalid Project Path")
        }

        if (state.validProjectPath) {
            Text(state.packageName ?: "Could not find package name")
        }

        TextField(state.serviceName, { state.onServiceName(it) })
        if (!state.validServiceName) {
            Text("Invalid gradle module name")
        }

        for (subproject in state.subprojects) {
            GradleSubprojectField(
                isAndroid = subproject.isAndroid,
                suffix = subproject.suffix,
                onAndroidChecked = { checked -> state.onAndroidChecked(subproject, checked) }
            )
        }

        Button(
            onClick = { state.onCreate() },
            enabled = state.validServiceState
        ) {
            Text("Create")
        }
    }
}

@Composable
fun GradleSubprojectField(
    modifier: Modifier = Modifier,
    isAndroid: Boolean = false,
    suffix: String,
    onAndroidChecked: ((Boolean) -> Unit)? = null
) {
    Row(modifier) {
        Text(suffix)
        if (onAndroidChecked != null) {
            Checkbox(isAndroid, onAndroidChecked)
        }
    }
}