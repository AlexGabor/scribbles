package renamePackage.usecase

import java.io.File

fun interface RenamePackage {
    operator fun invoke(projectPath: String, oldPackage: String, newPackage: String)
}

class RenamePackageUseCase : RenamePackage {
    override fun invoke(projectPath: String, oldPackage: String, newPackage: String) {
        val projectFile = File(projectPath)
        val oldPackagePath = oldPackage.replace('.', '/')
        val newPackagePath = newPackage.replace('.', '/')

        renameDirectories(projectFile, oldPackagePath, newPackagePath)
        deleteOldPackage(projectFile, oldPackagePath)
    }

    private fun deleteOldPackage(file: File, oldPackagePath: String) {
        if (file.list().isNullOrEmpty() && file.absolutePath.contains(oldPackagePath)) {
            var toBeDeleted = file
            for(i in oldPackagePath.split("/").indices) {
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