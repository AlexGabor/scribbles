import androidx.compose.runtime.*
import projectInfo.usecase.*

@Composable
fun rememberProjectInfoState(
    isValidProjectPath: IsValidProjectPath = IsValidProjectPathUseCase(),
    findPackageName: FindApplicationPackage = FindApplicationPackageUseCase(),
): ProjectInfoState {
    return remember { ProjectInfoState(isValidProjectPath, findPackageName) }
}

class ProjectInfoState(
    isValidProjectPath: IsValidProjectPath,
    findPackageName: FindApplicationPackage,
) {

    var selectedPath: String? by mutableStateOf(null)
    val validProjectPath: Boolean by derivedStateOf {
        selectedPath?.let { isValidProjectPath(it) != ProjectValidationResult.DoesNotExist } ?: false
    }

    val packageName: String? by derivedStateOf {
        if (validProjectPath) {
            val path = selectedPath ?: return@derivedStateOf null
            val packageResult = findPackageName(path)
            if (packageResult is ApplicationPackageResult.Package) packageResult.name else null
        } else null
    }
}