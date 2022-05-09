package newservice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import newservice.usecase.ApplicationPackageResult
import newservice.usecase.FindApplicationPackage
import newservice.usecase.FindApplicationPackageUseCase
import newservice.usecase.IsValidProjectPath
import newservice.usecase.IsValidProjectPathUseCase
import newservice.usecase.ProjectValidationResult

@Composable
fun rememberScreenState(
    isValidProjectPath: IsValidProjectPath = IsValidProjectPathUseCase(),
    findPackageName: FindApplicationPackage = FindApplicationPackageUseCase(),
): NewServiceState {
    return remember { NewServiceState(isValidProjectPath, findPackageName) }
}

class NewServiceState(
    isValidProjectPath: IsValidProjectPath,
    findPackageName: FindApplicationPackage,
) {
    var selectedPath: String? by mutableStateOf(null)
        private set
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

    var serviceName: String by mutableStateOf("")
        private set

    var subprojects: Map<Subproject, SubprojectConfiguration> by mutableStateOf(
        mapOf(
            Subproject.Api to SubprojectConfiguration(false),
            Subproject.Implementation to SubprojectConfiguration(false),
            Subproject.Test to SubprojectConfiguration(false),
        )
    )

    fun onPath(selectedFile: String) {
        selectedPath = selectedFile
    }

    fun onServiceName(name: String) {
        serviceName = name
    }

    fun onAndroidChecked(subproject: Subproject, checked: Boolean) {
        subprojects = subprojects.toMutableMap().apply {
            this[subproject] = this[subproject]!!.copy(isAndroid = checked)
        }
    }
}

data class SubprojectConfiguration(
    val isAndroid: Boolean,
)

enum class Subproject(val suffix: String) {
    Api("api"),
    Implementation("implementation"),
    Test("test"),
}