package cli


fun parseArgs(args: Array<String>): CliValues = CliValues(
    projectPath = findProjectPath(args),
    feature = findFeature(args)
)

private fun findFeature(args: Array<String>): Feature? {
    if (args.isEmpty()) return null
    return when (args[0]) {
        "new-service", "ns" -> Feature.NewService
        "rename-package", "rp" -> Feature.RenamePackage
        else -> null
    }
}

private fun findProjectPath(args: Array<String>): String? {
    for ((index, arg) in args.withIndex()) {
        if (arg == "-p" && index < args.size - 1)
            return args[index + 1]
    }
    return null
}

data class CliValues(
    val projectPath: String? = null,
    val feature: Feature? = null,
)

enum class Feature {
    NewService, RenamePackage
}