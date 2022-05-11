package newService

import androidx.compose.runtime.*
import newService.model.NewService
import newService.model.PredefinedSubproject
import newService.model.Project
import newService.model.Subproject
import newService.usecase.CreateSubprojects
import newService.usecase.CreateSubprojectsUseCase
import newService.usecase.IsValidServiceName
import newService.usecase.IsValidServiceNameUseCase

@Composable
fun rememberNewServiceScreenState(
    isValidServiceName: IsValidServiceName = IsValidServiceNameUseCase(),
    createSubprojects: CreateSubprojects = CreateSubprojectsUseCase(),
): NewServiceState {
    return remember { NewServiceState(isValidServiceName, createSubprojects) }
}

class NewServiceState(
    isValidServiceName: IsValidServiceName,
    private val createSubprojects: CreateSubprojects,
) {
    var selectedPath: String? by mutableStateOf(null)
    var packageName: String? by mutableStateOf(null)

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