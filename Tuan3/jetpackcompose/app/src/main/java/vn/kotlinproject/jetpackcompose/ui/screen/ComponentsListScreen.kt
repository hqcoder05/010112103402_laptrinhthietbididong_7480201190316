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
            TopAppBar(
                title = {
                    Text(
                        "UI Components List",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Display section
            item { SectionHeader("Display") }
            items(displayItems) { item ->
                UiTile(item = item) { onItemClick(item.title) }
            }

            // Input section
            item { SectionHeader("Input") }
            items(inputItems) { item ->
                UiTile(item = item) { onItemClick(item.title) }
            }

            // Layout section
            item { SectionHeader("Layout") }
            items(layoutItems) { item ->
                UiTile(item = item) { onItemClick(item.title) }
            }

            // Tự tìm hiểu section
            item { SectionHeader("Tự tìm hiểu") }
            item {
                UiTile(
                    item = UiItem("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản"),
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ) { onItemClick("Explore") }
            }

            // thêm khoảng trống cuối
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun UiTile(
    item: UiItem,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                item.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}