package newservice

import androidx.compose.runtime.*

@Composable
fun rememberScreenState(): NewServiceState {
    return remember { NewServiceState() }
}

class NewServiceState {
    var selectedPath: String? by mutableStateOf(null)

    fun onPath(selectedFile: String) {
        selectedPath = selectedFile
    }
}