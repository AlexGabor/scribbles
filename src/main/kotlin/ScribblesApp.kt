import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cli.CliValues
import newservice.NewServiceScreen
import newservice.rememberNewServiceScreenState
import theme.ScribblesTheme


fun applicationWindow(cliValues: CliValues) = application {
    Window(onCloseRequest = ::exitApplication) {
        ScribblesApp(cliValues)
    }
}

@Composable
fun ScribblesApp(cliValues: CliValues) {
    ScribblesTheme {
        NewServiceScreen(cliValues)
    }
}

@Composable
fun NewServiceScreen(cliValues: CliValues) {
    NewServiceScreen(rememberNewServiceScreenState().apply {
        onPath(cliValues.projectPath ?: System.getProperty("user.dir"))
    })
}