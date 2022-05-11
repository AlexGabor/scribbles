import androidx.compose.runtime.*

@Composable
fun rememberNavigationState(): NavigationState {
    return remember { NavigationState() }
}

class NavigationState {
    var screen: Screen by mutableStateOf(Screen.Home)
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