package cli


fun parseArgs(args: Array<String>): CliArgsResult = when {
    args.size == 1 && args[0] == "newService" -> CliArgsResult.ShowWindow(CliValues.NewService())
    args.size == 3 && args[0] == "newService" && args[1] == "-p" -> CliArgsResult.ShowWindow(
        CliValues.NewService(projectPath = args[2])
    )
    else -> CliArgsResult.ShowWindow(CliValues.Root)
}

sealed class CliArgsResult {
    class ShowWindow(val values: CliValues) : CliArgsResult()
    object Exit : CliArgsResult()
}

sealed class CliValues {
    class NewService(val projectPath: String? = null) : CliValues()
    object Root : CliValues()
}
