package newservice.usecase

import newservice.Subproject
import newservice.SubprojectConfiguration
import newservice.template.Template
import newservice.template.fillTemplate
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
        subprojects.forEach { subproject ->
            createSubproject(path, serviceName, packageName, subproject.key, subproject.value)
        }
    }

    private fun createSubproject(
        projectPath: String,
        serviceName: String,
        packageName: String,
        subproject: Subproject,
        configuration: SubprojectConfiguration
    ) {
        val serviceSubPath = serviceName.split(":").joinToString(separator = "/") { it }
        val serviceAbsoulutePath = "$projectPath$serviceSubPath-${subproject.suffix}"
        createPackagePath("$serviceAbsoulutePath/src/main/java/", packageName)
        createGradleFile(
            fileName = "$serviceAbsoulutePath/build.gradle.kts",
            subproject = subproject,
            isAndroid = configuration.isAndroid,
            serviceName = serviceName,
            packageName = packageName
        )
        if (configuration.isAndroid) {
            createManifestFile(
                fileName = "$serviceAbsoulutePath/src/main/AndroidManifest.xml",
                subproject = subproject,
                serviceName = serviceName,
                packageName = packageName
            )
        }
    }

    private fun createPackagePath(path: String, packageName: String) {
        val packagePath = packageName.split(".").joinToString(separator = "/") { it }
        File("$path/$packagePath").mkdirs()
    }

    private fun createGradleFile(
        fileName: String,
        subproject: Subproject,
        isAndroid: Boolean,
        serviceName: String,
        packageName: String,
    ) {
        val template = when (subproject) {
            Subproject.Api -> if (isAndroid) Template.GradleAndroidApi else Template.GradleJvmApi
            Subproject.Implementation -> if (isAndroid) Template.GradleAndroidImplementation else Template.GradleJvmImplementation
            Subproject.Test -> if (isAndroid) Template.GradleAndroidTest else Template.GradleJvmTest
        }
        val file = File(fileName)
        file.createNewFile()
        file.writeText(
            template.fillTemplate(
                serviceName = serviceName,
                packageName = packageName,
                suffix = subproject.suffix
            )
        )
    }

    private fun createManifestFile(
        fileName: String,
        subproject: Subproject,
        serviceName: String,
        packageName: String,
    ) {
        val file = File(fileName)
        file.createNewFile()
        file.writeText(
            Template.Manifest.fillTemplate(
                serviceName = serviceName,
                packageName = packageName,
                suffix = subproject.suffix
            )
        )
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