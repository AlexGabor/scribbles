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
    override fun hashCode(): Int = suffix.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Subproject

        if (suffix != other.suffix) return false
        if (isAndroid != other.isAndroid) return false

        return true
    }
}

enum class PredefinedSubproject(val suffix: String) {
    Api("api"),
    Implementation("implementation"),
    Test("test"),
}