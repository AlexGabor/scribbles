package directorybrowser

import javax.swing.JFileChooser

interface DirectoryBrowser {
    fun showDialog(): DirectoryResult
}

class SwingDirectoryChooser : DirectoryBrowser {
    override fun showDialog(): DirectoryResult {
        val chooser = JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        }

        val choice = chooser.showOpenDialog(null)
        if (choice == JFileChooser.APPROVE_OPTION) {
            return DirectoryResult.Selection(chooser.selectedFile.toString())
        }

        return DirectoryResult.Dismiss
    }
}

sealed class DirectoryResult {
    object Dismiss : DirectoryResult()
    class Selection(val path: String) : DirectoryResult()
}