package cli


fun parseArgs(args: Array<String>): CliValues = CliValues(
    projectPath = findProjectPath(args)
)

fun findProjectPath(args: Array<String>): String? {
    for ((index, arg) in args.withIndex()) {
        if (arg == "-p" && index < args.size - 1)
            return args[index + 1]
    }
    return null
}

data class CliValues(
    val projectPath: String? = null,
)