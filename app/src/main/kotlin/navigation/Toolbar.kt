package navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.useResource


@Composable // TODO make it a slot api and take it out of navigation
fun Toolbar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    val density = LocalDensity.current
    val backIcon: Painter = remember(density) { useResource("icon-back.svg") { loadSvgPainter(it, density) } }
    Row(modifier.fillMaxWidth()) {
        Box(Modifier.weight(1f)) {
            IconButton(onClick = onBack) {
                Image(painter = backIcon, contentDescription = null)
            }
        }
//        Title(text)
    }
}