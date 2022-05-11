package home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.Body
import theme.Title

@Composable
fun Home(
    onEntryClick: (entry: Entry) -> Unit,
) {
    LazyColumn {
        items(Entry.values()) { entry ->
            Entry(entry.text, Modifier.clickable { onEntryClick(entry) })
        }
    }
}

enum class Entry(val text: String) {
    NewService("New service"),
    RenamePackage("Rename Package")
}

@Composable
private fun Entry(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier.fillMaxWidth()
            .height(64.dp)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Title(text)
    }
}