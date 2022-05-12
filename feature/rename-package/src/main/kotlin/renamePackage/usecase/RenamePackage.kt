package renamePackage.usecase

import java.io.File

fun interface RenamePackage {
    operator fun invoke(projectPath: String, oldPackage: String, newPackage: String)
}

class RenamePackageUseCase : RenamePackage {
    private val fileSeparator = System.getProperty("file.separator")
    override fun invoke(projectPath: String, oldPackage: String, newPackage: String) {
        val projectFile = File(projectPath)
        val oldPackagePath = oldPackage.replace(".", fileSeparator)
        val newPackagePath = newPackage.replace(".", fileSeparator)

        renameDirectories(projectFile, oldPackagePath, newPackagePath)
        deleteOldPackage(projectFile, oldPackagePath)
    }

    private fun deleteOldPackage(file: File, oldPackagePath: String) {
        if (file.list().isNullOrEmpty() && file.absolutePath.contains(oldPackagePath)) {
            var toBeDeleted = file
            for(i in oldPackagePath.split(fileSeparator).indices) {
                toBeDeleted.delete()
                toBeDeleted = toBeDeleted.parentFile
            }
        } else {
            file.listFiles()?.forEach { deleteOldPackage(it, oldPackagePath) }
        }
    }

    private fun renameDirectories(file: File, oldPackagePath: String, newPackagePath: String) {
        if (file.absolutePath.contains(newPackagePath)) return

        val parentFile = file.parentFile
        if (parentFile.absolutePath.contains(oldPackagePath)) {
            val newParentPath = parentFile.absolutePath.replace(oldPackagePath, newPackagePath)
            File(newParentPath).mkdirs()
            file.renameTo(File(file.absolutePath.replace(oldPackagePath, newPackagePath)))
        } else {
            file.listFiles()?.forEach { renameDirectories(it, oldPackagePath, newPackagePath) }
        }
    }
}