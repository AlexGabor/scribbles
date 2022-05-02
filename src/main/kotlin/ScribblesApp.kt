import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import newservice.NewServiceScreen
import theme.ScribblesTheme

@Composable
@Preview
fun ScribblesApp() {
    ScribblesTheme {
        NewServiceScreen()
    }
}