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

        TextField(state.serviceName, { state.onServiceName(it) })

        GradleSubprojectField(
            isAndroid = state.subprojects[Subproject.Api]!!.isAndroid,
            suffix = Subproject.Api.suffix,
            onAndroidChecked = { checked -> state.onAndroidChecked(Subproject.Api, checked) }
        )
        GradleSubprojectField(
            isAndroid = state.subprojects[Subproject.Implementation]!!.isAndroid,
            suffix = Subproject.Implementation.suffix,
            onAndroidChecked = { checked -> state.onAndroidChecked(Subproject.Implementation, checked) }
        )
        GradleSubprojectField(
            isAndroid = state.subprojects[Subproject.Implementation]!!.isAndroid,
            suffix = Subproject.Test.suffix,
            onAndroidChecked = { }
        )

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