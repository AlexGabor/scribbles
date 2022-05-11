package projectInfo.usecase

import java.io.File

fun interface IsValidProjectPath {
    operator fun invoke(path: String): ProjectValidationResult
}

sealed class ProjectValidationResult {
    object DoesNotExist : ProjectValidationResult()
    class Exist(isKts: Boolean) : ProjectValidationResult()
}

class IsValidProjectPathUseCase : IsValidProjectPath {
    override fun invoke(path: String): ProjectValidationResult {
        val projectPath = if (path.endsWith("/")) path else "$path/"
        val settingsPath = projectPath + "settings.gradle"
        val settingsFile = File(settingsPath)
        val settingsKtsFile = File("$settingsPath.kts")
        return when {
            settingsFile.exists() -> ProjectValidationResult.Exist(false)
            settingsKtsFile.exists() -> ProjectValidationResult.Exist(true)
            else -> ProjectValidationResult.DoesNotExist
        }
    }
}