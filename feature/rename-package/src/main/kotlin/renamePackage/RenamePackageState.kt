package renamePackage

import androidx.compose.runtime.*
import renamePackage.usecase.RenamePackage
import renamePackage.usecase.RenamePackageUseCase

@Composable
fun rememberRenamePackageState(
    renamePackage: RenamePackage = RenamePackageUseCase()
): RenamePackageState {
    return remember { RenamePackageState(renamePackage) }
}

class RenamePackageState(
    private val renamePackage: RenamePackage
) {
    var selectedPath: String? by mutableStateOf(null)

    var packageName: String? by mutableStateOf(null)
    var newPackageName: String by mutableStateOf("")

    val isValidPackageName: Boolean by derivedStateOf {
        selectedPath != null &&
        packageName != null &&
        newPackageName.matches(Regex("[a-zA-Z]+[a-zA-Z0-9_]*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)*"))
    }

    fun onRename() {
        renamePackage(
            projectPath = selectedPath!!,
            oldPackage = packageName!!,
            newPackage = newPackageName
        )
    }
}
