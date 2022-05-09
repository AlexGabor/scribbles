package newservice.usecase

import newservice.Subproject
import newservice.SubprojectConfiguration
import java.io.File

fun interface CreateSubprojects {
    operator fun invoke(
        path: String,
        serviceName: String,
        packageName: String,
        subprojects: Map<Subproject, SubprojectConfiguration>
    )
}

class CreateSubprojectsUseCase : CreateSubprojects {
    override fun invoke(
        path: String,
        serviceName: String,
        packageName: String,
        subprojects: Map<Subproject, SubprojectConfiguration>
    ) {
        val projectPath = if (path.endsWith("/")) path else "$path/"
        addToSettings(projectPath, serviceName, subprojects)
    }

    private fun addToSettings(
        path: String,
        serviceName: String,
        subprojects: Map<Subproject, SubprojectConfiguration>
    ) {
        val subprojectNames = subprojects.keys.map { "$serviceName-${it.suffix}" }
        val settingsFile = getSettingsFile(path)
        val includeList = subprojectNames.joinToString(separator = ", ") { "\"$it\"" }
        settingsFile.appendText(
            """
            include($includeList)
        """.trimIndent() + "\n"
        )
    }

    private fun getSettingsFile(path: String): File {
        val settingsPath = path + "settings.gradle"
        val settingsFile = File(settingsPath)
        val settingsKtsFile = File("$settingsPath.kts")
        return when {
            settingsFile.exists() -> settingsFile
            settingsKtsFile.exists() -> settingsKtsFile
            else -> throw IllegalStateException("Could not find settings.gradle[.kts] file")
        }
    }
}