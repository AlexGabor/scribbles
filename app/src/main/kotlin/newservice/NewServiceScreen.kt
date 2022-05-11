package newservice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import directorybrowser.DirectoryResult
import directorybrowser.SelectDirectoryButton
import theme.Body
import theme.Title


@Composable
fun NewServiceScreen(
    state: NewServiceState = rememberNewServiceScreenState(),
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { ProjectInfo(state) }
        item { ServiceName(state) }
        item { Subprojects(state) }
        item { CreateButton(state) }
    }
}

@Composable
private fun CreateButton(state: NewServiceState) {
    Button(
        onClick = { state.onCreate() },
        enabled = state.validServiceState
    ) {
        Body("Create")
    }
}

@Composable
private fun Subprojects(state: NewServiceState) {
    Column {
        Title("Subprojects:")
        for (subproject in state.subprojects) {
            GradleSubprojectField(
                isAndroid = subproject.isAndroid,
                suffix = subproject.suffix,
                onAndroidChecked = { checked -> state.onAndroidChecked(subproject, checked) }
            )
        }
    }
}

@Composable
private fun ServiceName(state: NewServiceState) {
    Column {
        Title("Service name:")
        TextField(state.serviceName, { state.onServiceName(it) })
        if (!state.validServiceName) {
            Body("Invalid gradle module name")
        }
    }
}

@Composable
private fun ProjectInfo(state: NewServiceState) {
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
            if (directoryResult is DirectoryResult.Selection) state.onPath(directoryResult.path)
        })
    }
}

@Composable
fun GradleSubprojectField(
    modifier: Modifier = Modifier,
    isAndroid: Boolean = false,
    suffix: String,
    onAndroidChecked: ((Boolean) -> Unit)? = null
) {
    Column(modifier) {
        Body(suffix)
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onAndroidChecked != null) {
                Checkbox(isAndroid, onAndroidChecked)
                Body("is Android module")
            }
        }
    }
}