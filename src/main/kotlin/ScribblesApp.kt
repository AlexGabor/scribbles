import androidx.compose.desktop.ui.tooling.preview.Preview
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
@Preview
fun ScribblesApp(cliValues: CliValues) {
    ScribblesTheme {
        when (cliValues) {
            is CliValues.NewService -> NewServiceScreen(rememberNewServiceScreenState().apply {
                onPath(cliValues.projectPath ?: System.getProperty("user.dir"))
            })
            CliValues.Root -> NewServiceScreen()
        }
    }
}