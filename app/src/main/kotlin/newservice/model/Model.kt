package newservice.model

class Project(absolutePath: String, val packageName: String) {
    val absolutePath: String = if (absolutePath.endsWith("/")) absolutePath else "$absolutePath/"
    val packageNameAsPath = packageName.split(".").joinToString(separator = "/") { it }
}

data class NewService(
    val gradleName: String,
    val subprojects: Iterable<Subproject>
) {
    val gradleNameAsPath = gradleName.split(":").joinToString(separator = "/") { it }
    val lastNameSegment = gradleName.split(":").last()
}

data class Subproject(
    val suffix: String,
    val isAndroid: Boolean = false,
) {

    fun getGradleName(service: NewService): String = "${service.gradleName}:${service.lastNameSegment}-$suffix"

    fun getGradleNameAsPath(service: NewService): String {
        return getGradleName(service).split(":").joinToString(separator = "/") { it }
    }
}

enum class PredefinedSubproject(val suffix: String) {
    Api("api"),
    Implementation("implementation"),
    Test("test"),
}