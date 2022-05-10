package newservice.usecase

import newservice.model.NewService
import newservice.model.PredefinedSubproject
import newservice.model.Project
import newservice.model.Subproject
import newservice.template.Template
import newservice.template.fillTemplate
import java.io.File

fun interface CreateSubprojects {
    operator fun invoke(
        project: Project,
        newService: NewService,
    )
}

class CreateSubprojectsUseCase : CreateSubprojects {
    override fun invoke(
        project: Project,
        newService: NewService,
    ) {
        addToSettings(project, newService)
        newService.subprojects.forEach { subproject ->
            createSubproject(project, newService, subproject)
        }
    }

    private fun createSubproject(project: Project, newService: NewService, subproject: Subproject) {
        val servicePath = newService.gradleNameAsPath
        val serviceAbsolutePathWithSuffix = "${project.absolutePath}$servicePath/${newService.lastNameSegment}-${subproject.suffix}/"
        createPackagePath(serviceAbsolutePathWithSuffix, project, newService)
        createGradleFile(
            fileName = "$serviceAbsolutePathWithSuffix/build.gradle.kts",
            subproject = subproject,
            isAndroid = subproject.isAndroid,
            serviceName = newService.gradleName,
            packageName = project.packageName,
        )
        if (subproject.isAndroid) {
            createManifestFile(
                fileName = "$serviceAbsolutePathWithSuffix/src/main/AndroidManifest.xml",
                subproject = subproject,
                serviceName = newService.gradleName,
                packageName = project.packageName,
            )
        }
    }

    private fun createPackagePath(serviceAbsolutePathWithSuffix: String, project: Project, newService: NewService) {
        File("${serviceAbsolutePathWithSuffix}src/main/java/${project.packageNameAsPath}/${newService.lastNameSegment}").mkdirs()
    }

    private fun createGradleFile(
        fileName: String,
        subproject: Subproject,
        isAndroid: Boolean,
        serviceName: String,
        packageName: String,
    ) {
        val template = when (subproject.suffix) {
            PredefinedSubproject.Api.suffix -> if (isAndroid) Template.GradleAndroidApi else Template.GradleJvmApi
            PredefinedSubproject.Implementation.suffix -> if (isAndroid) Template.GradleAndroidImplementation else Template.GradleJvmImplementation
            PredefinedSubproject.Test.suffix -> if (isAndroid) Template.GradleAndroidTest else Template.GradleJvmTest
            else -> Template.GradleDefault
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
        project: Project,
        newService: NewService,
    ) {
        val subprojectNames = newService.subprojects.map { "${newService.gradleName}:${newService.lastNameSegment}-${it.suffix}" }
        val settingsFile = getSettingsFile(project.absolutePath)
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