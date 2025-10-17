package vn.kotlinproject.jetpackcompose.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class UiItem(val title: String, val subtitle: String)

private val displayItems = listOf(
    UiItem("Text", "Displays text"),
    UiItem("Image", "Displays an image"),
)
private val inputItems = listOf(
    UiItem("TextField", "Input field for text"),
    UiItem("PasswordField", "Input field for passwords"),
)
private val layoutItems = listOf(
    UiItem("Column", "Arranges elements vertically"),
    UiItem("Row", "Arranges elements horizontally"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsListScreen(
    onItemClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            // Tiêu đề canh giữa + màu xanh giống ảnh
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "UI Components List",
                        color = Color(0xFF26A5E4), // xanh dương như hình
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Display
            item { SectionHeader("Display") }
            items(displayItems) { item ->
                UiTileBlue(item = item) { onItemClick(item.title) }
            }

            // Input
            item { SectionHeader("Input") }
            items(inputItems) { item ->
                UiTileBlue(item = item) { onItemClick(item.title) }
            }

            // Layout
            item { SectionHeader("Layout") }
            items(layoutItems) { item ->
                UiTileBlue(item = item) { onItemClick(item.title) }
            }

            // Thẻ đỏ "Tự tìm hiểu"
            item {
                UiTileColored(
                    item = UiItem("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản"),
                    container = MaterialTheme.colorScheme.errorContainer,
                    content = MaterialTheme.colorScheme.onErrorContainer
                ) { onItemClick("Explore") }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(start = 4.dp, top = 8.dp, bottom = 4.dp)
    )
}

/* --- Tiles --- */

@Composable
private fun UiTileBlue(
    item: UiItem,
    onClick: () -> Unit
) {
    // xanh nhạt giống screenshot
    val lightBlue = Color(0xFFD6ECFF)

    UiTileColored(
        item = item,
        container = lightBlue,
        content = MaterialTheme.colorScheme.onSurface,
        onClick = onClick
    )
}

@Composable
private fun UiTileColored(
    item: UiItem,
    container: Color,
    content: Color,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = container,
            contentColor = content
        ),
        shape = MaterialTheme.shapes.large, // bo tròn hơn
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
