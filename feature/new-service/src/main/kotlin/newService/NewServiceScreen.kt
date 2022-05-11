package newService

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import projectInfo.ProjectInfo
import rememberProjectInfoState
import theme.Body
import theme.Title

@Composable
fun NewServiceScreen(
    state: NewServiceState = rememberNewServiceScreenState(),
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