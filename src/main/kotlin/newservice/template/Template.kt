package newservice.template

object Template {

    object Field {
        const val serviceName = "{serviceName}"
        const val packageName = "{packageName}"
        const val suffix = "{suffix}"
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
        implementation(project("${Field.serviceName}-api"))
    }
    """.trimIndent()

    val GradleTestDependencies = """
    dependencies {
        implementation(project("${Field.serviceName}-implementation"))
    }
    """.trimIndent()

    val Manifest = """
    <?xml version="1.0" encoding="utf-8"?>
    <manifest package="${Field.packageName}.${Field.suffix}">
    
    </manifest>
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
    serviceName: String,
    packageName: String,
    suffix: String,
): String {
    return this.replace(Template.Field.serviceName, serviceName)
        .replace(Template.Field.packageName, packageName)
        .replace(Template.Field.suffix, suffix)
}
