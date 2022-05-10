package newservice.template

import newservice.model.NewService
import newservice.model.Project
import newservice.model.Subproject

object Template {

    object Field {
        const val serviceGradleName = "{serviceGradleName}"
        const val projectPackageName = "{projectPackageName}"
        const val suffix = "{suffix}"
        const val serviceLastNameSegment = "{serviceLastNameSegment}"
    }

    val GradleAndroidPlugins = """
    plugins {
        id("com.android.library")
        id("org.jetbrains.kotlin.android")
    }
    """.trimIndent()

    val GradleJvmPlugins = """
    plugins {
        id("org.jetbrains.kotlin.jvm")
    }
    """.trimIndent()

    val GradleApiDependencies = """
    dependencies {
    
    }
    """.trimIndent()

    val GradleImplementationDependencies = """
    dependencies {
        implementation(project("${Field.serviceGradleName}:${Field.serviceLastNameSegment}-api"))
    }
    """.trimIndent()

    val GradleTestDependencies = """
    dependencies {
        implementation(project("${Field.serviceGradleName}:${Field.serviceLastNameSegment}-implementation"))
    }
    """.trimIndent()

    val Manifest = """
    <?xml version="1.0" encoding="utf-8"?>
    <manifest package="${Field.projectPackageName}.${Field.suffix}" />
    """.trimIndent()

    val GradleAndroidApi = "$GradleAndroidPlugins\n\n$GradleApiDependencies\n"

    val GradleAndroidImplementation = "$GradleAndroidPlugins\n\n$GradleImplementationDependencies\n"

    val GradleAndroidTest = "$GradleAndroidPlugins\n\n$GradleTestDependencies\n"

    val GradleJvmApi = "$GradleJvmPlugins\n\n$GradleApiDependencies\n"

    val GradleJvmImplementation = "$GradleJvmPlugins\n\n$GradleImplementationDependencies\n"

    val GradleJvmTest = "$GradleJvmPlugins\n\n$GradleTestDependencies\n"

    val GradleDefault = "$GradleJvmPlugins\n\n$GradleApiDependencies\n"
}

fun String.fillTemplate(
    project: Project,
    newService: NewService,
    subproject: Subproject,
): String {
    return this.replace(Template.Field.serviceGradleName, newService.gradleName)
        .replace(Template.Field.serviceLastNameSegment, newService.lastNameSegment)
        .replace(Template.Field.projectPackageName, project.packageName)
        .replace(Template.Field.suffix, subproject.suffix)
}
