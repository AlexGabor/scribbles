package newservice.usecase

import java.io.File

fun interface FindApplicationPackage {
    operator fun invoke(projectPath: String): ApplicationPackageResult
}

sealed class ApplicationPackageResult {
    object ManifestNotFound: ApplicationPackageResult()
    object PackageNotFound: ApplicationPackageResult()
    class Package(val name: String): ApplicationPackageResult()
}

class FindApplicationPackageUseCase : FindApplicationPackage {
    override fun invoke(projectPath: String): ApplicationPackageResult {
        val root = if (projectPath.endsWith("/")) projectPath else "$projectPath/"
        val manifestPath = root + "app/src/main/AndroidManifest.xml"
        val manifestFile = File(manifestPath)

        if (!manifestFile.exists()) return ApplicationPackageResult.ManifestNotFound
        val packageName = manifestFile.useLines { lines ->
            lines.mapNotNull { line ->
                val regex = Regex("package=\"(.*)\"")
                val match = regex.find(line)
                match?.groupValues?.getOrNull(1)
            }.firstOrNull()
        }
        if (packageName != null) {
            return ApplicationPackageResult.Package(packageName)
        }
        return ApplicationPackageResult.PackageNotFound
    }
}