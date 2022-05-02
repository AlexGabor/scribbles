package newservice

import androidx.compose.runtime.*
import java.io.File

@Composable
fun rememberScreenState(): NewServiceState {
    return remember { NewServiceState() }
}

class NewServiceState {
    var selectedPath: String? by mutableStateOf(null)
        private set
    val validProjectPath by derivedStateOf { selectedPath?.let { isValidProjectPath(it) } ?: false }

    fun onPath(selectedFile: String) {
        selectedPath = selectedFile
    }
}

private fun isValidProjectPath(path: String): Boolean {
    val projectPath = if (path.endsWith("/")) path else "$path/"
    val settingsPath = projectPath + "settings.gradle"
    val settingsFile = File(settingsPath)
    val settingsKtsFile = File("$settingsPath.kts")
    return settingsFile.exists() || settingsKtsFile.exists()
}