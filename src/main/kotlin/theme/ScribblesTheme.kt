package theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

@Composable
fun ScribblesTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = darkColors(),
        content = content
    )
}