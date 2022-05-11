import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cli.CliValues
import cli.Feature
import home.Entry
import home.Home
import navigation.Toolbar
import newService.NewServiceState
import newService.rememberNewServiceScreenState

@Composable
fun Navigation(
    cliValues: CliValues,
    state: NavigationState = rememberNavigationState(cliValues.feature.toScreen()),
) {
    Crossfade(state.screen) { screen ->
        Column {
            if (screen != Screen.Home) {
                Toolbar(onBack = { state.navigate(Screen.Home) })
            }
            when (screen) {
                Screen.Home -> Home(onEntryClick = { entry ->
                    state.navigate(entry.toScreen())
                })
                Screen.NewService -> NewServiceScreen(cliValues)
                Screen.RenamePackage -> {
                    Text("RenamePackage")
                }
            }
        }
    }
}

@Composable
fun NewServiceScreen(
    cliValues: CliValues,
    state: NewServiceState = rememberNewServiceScreenState(),
) {
    newService.NewServiceScreen(state.apply {
        onPath(cliValues.projectPath ?: System.getProperty("user.dir"))
    })
}

private fun Entry.toScreen(): Screen = when (this) {
    Entry.NewService -> Screen.NewService
    Entry.RenamePackage -> Screen.RenamePackage
}

private fun Feature?.toScreen(): Screen = when (this) {
    Feature.NewService -> Screen.NewService
    Feature.RenamePackage -> Screen.RenamePackage
    null -> Screen.Home
}