package newservice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import newservice.model.NewService
import newservice.model.PredefinedSubproject
import newservice.model.Project
import newservice.model.Subproject
import newservice.usecase.ApplicationPackageResult
import newservice.usecase.CreateSubprojects
import newservice.usecase.CreateSubprojectsUseCase
import newservice.usecase.FindApplicationPackage
import newservice.usecase.FindApplicationPackageUseCase
import newservice.usecase.IsValidProjectPath
import newservice.usecase.IsValidProjectPathUseCase
import newservice.usecase.IsValidServiceName
import newservice.usecase.IsValidServiceNameUseCase
import newservice.usecase.ProjectValidationResult

@Composable
fun rememberNewServiceScreenState(
    isValidProjectPath: IsValidProjectPath = IsValidProjectPathUseCase(),
    findPackageName: FindApplicationPackage = FindApplicationPackageUseCase(),
    isValidServiceName: IsValidServiceName = IsValidServiceNameUseCase(),
    createSubprojects: CreateSubprojects = CreateSubprojectsUseCase(),
): NewServiceState {
    return remember { NewServiceState(isValidProjectPath, findPackageName, isValidServiceName, createSubprojects) }
}

class NewServiceState(
    isValidProjectPath: IsValidProjectPath,
    findPackageName: FindApplicationPackage,
    isValidServiceName: IsValidServiceName,
    private val createSubprojects: CreateSubprojects,
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

    var serviceName: String by mutableStateOf(":newService")
        private set
    val validServiceName: Boolean by derivedStateOf { isValidServiceName(serviceName) }

    var subprojects: List<Subproject> by mutableStateOf(
        listOf(
            Subproject(PredefinedSubproject.Api.suffix),
            Subproject(PredefinedSubproject.Implementation.suffix),
            Subproject(PredefinedSubproject.Test.suffix),
        )
    )

    val validServiceState: Boolean by derivedStateOf {
        selectedPath != null && packageName != null
    }

    fun onPath(selectedFile: String) {
        selectedPath = selectedFile
    }

    fun onServiceName(name: String) {
        serviceName = name
    }

    fun onAndroidChecked(subproject: Subproject, checked: Boolean) {
        subprojects = subprojects.map {
            if (it == subproject) it.copy(isAndroid = checked) else it
        }
    }

    fun onCreate() {
        createSubprojects(
            project = Project(selectedPath!!, packageName!!),
            newService = NewService(serviceName, subprojects)
        )
    }
}