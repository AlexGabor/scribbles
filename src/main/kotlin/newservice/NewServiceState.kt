package newservice

import androidx.compose.runtime.*
import newservice.usecase.IsValidProjectPath
import newservice.usecase.IsValidProjectPathUseCase
import newservice.usecase.ProjectValidationResult

@Composable
fun rememberScreenState(
    isValidProjectPath: IsValidProjectPath = IsValidProjectPathUseCase()
): NewServiceState {
    return remember { NewServiceState(isValidProjectPath) }
}

class NewServiceState(
    isValidProjectPath: IsValidProjectPath
) {
    var selectedPath: String? by mutableStateOf(null)
        private set
    val validProjectPath by derivedStateOf {
        selectedPath?.let { isValidProjectPath(it) != ProjectValidationResult.DoesNotExist } ?: false
    }

    fun onPath(selectedFile: String) {
        selectedPath = selectedFile
    }
}