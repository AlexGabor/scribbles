package theme

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(text, modifier, fontWeight = FontWeight.Bold)
}

@Composable
fun Body(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(text, modifier)
}