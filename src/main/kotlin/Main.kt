import cli.CliArgsResult
import cli.parseArgs
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    when (val result = parseArgs(args)) {
        is CliArgsResult.ShowWindow -> applicationWindow(result.values)
        CliArgsResult.Exit -> exitProcess(0)
    }
}
