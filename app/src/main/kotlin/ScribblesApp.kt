import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cli.CliValues
import theme.ScribblesTheme


fun applicationWindow(cliValues: CliValues) = application {
    Window(onCloseRequest = ::exitApplication) {
        ScribblesApp(cliValues)
    }
}

@Composable
fun ScribblesApp(cliValues: CliValues) {
    ScribblesTheme {
        Navigation(cliValues = cliValues)
    }
}