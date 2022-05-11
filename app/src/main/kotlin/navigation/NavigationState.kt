import androidx.compose.runtime.*

@Composable
fun rememberNavigationState(initialScreen: Screen = Screen.Home): NavigationState {
    return remember { NavigationState(initialScreen) }
}

class NavigationState(initialScreen: Screen) {
    var screen: Screen by mutableStateOf(initialScreen)
        private set

    fun navigate(screen: Screen) {
        this.screen = screen
    }
}

sealed class Screen {
    object Home : Screen()
    object NewService : Screen()
    object RenamePackage : Screen()
}