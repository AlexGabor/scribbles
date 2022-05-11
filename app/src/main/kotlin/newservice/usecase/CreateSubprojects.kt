package newservice.usecase

import newservice.model.NewService
import newservice.model.PredefinedSubproject
import newservice.model.Project
import newservice.model.Subproject
import newservice.template.Template
import newservice.template.fillTemplate
import java.io.File
import newservice.converter.kebabToCamelCase

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
        val serviceAbsolutePathWithSuffix = "${project.absolutePath}${subproject.getGradleNameAsPath(newService)}/"
        createPackagePath(serviceAbsolutePathWithSuffix, project, newService)
        createGradleFile(
            fileName = "$serviceAbsolutePathWithSuffix/build.gradle.kts",
            project, newService, subproject
        )
        if (subproject.isAndroid) {
            createManifestFile(
                absoluteFileName = "$serviceAbsolutePathWithSuffix/src/main/AndroidManifest.xml",
                project, newService, subproject
            )
        }
    }

    private fun createPackagePath(serviceAbsolutePathWithSuffix: String, project: Project, newService: NewService) {
        File("${serviceAbsolutePathWithSuffix}src/main/java/${project.packageNameAsPath}/${newService.lastNameSegment.kebabToCamelCase()}").mkdirs()
    }

    private fun createGradleFile(
        fileName: String,
        project: Project,
        newService: NewService,
        subproject: Subproject,
    ) {
        val template = when (subproject.suffix) {
            PredefinedSubproject.Api.suffix -> if (subproject.isAndroid) Template.GradleAndroidApi else Template.GradleJvmApi
            PredefinedSubproject.Implementation.suffix -> if (subproject.isAndroid) Template.GradleAndroidImplementation else Template.GradleJvmImplementation
            PredefinedSubproject.Test.suffix -> if (subproject.isAndroid) Template.GradleAndroidTest else Template.GradleJvmTest
            else -> Template.GradleDefault
        }
        val file = File(fileName)
        file.createNewFile()
        file.writeText(
            template.fillTemplate(project, newService, subproject)
        )
    }

    private fun createManifestFile(
        absoluteFileName: String,
        project: Project,
        newService: NewService,
        subproject: Subproject,
    ) {
        val file = File(absoluteFileName)
        file.createNewFile()
        file.writeText(
            Template.Manifest.fillTemplate(project, newService, subproject)
        )
    }

    private fun addToSettings(
        project: Project,
        newService: NewService,
    ) {
        val subprojectNames = newService.subprojects.map { it.getGradleName(newService) }
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