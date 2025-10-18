package vn.kotlinproject.bttl.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAndLazyScreen(
    onBack: () -> Unit,
    onOpenDefault: () -> Unit,   // điều hướng sang DefaultColumnScreen
    onOpenLazy: () -> Unit       // điều hướng sang LazyColumnScreen
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List Demo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Card: Column (non-lazy)
            DemoCard(
                title = "Column (non-lazy)",
                subtitle = "Render toàn bộ phần tử vào cây. KHÔNG khuyến nghị cho danh sách lớn.",
                danger = true,
                onClick = onOpenDefault
            )

            // Card: LazyColumn
            DemoCard(
                title = "LazyColumn",
                subtitle = "Chỉ compose item đang hiển thị. Phù hợp cho danh sách lớn (vd 1.000.000).",
                danger = false,
                onClick = onOpenLazy
            )
        }
    }
}

@Composable
private fun DemoCard(
    title: String,
    subtitle: String,
    danger: Boolean,
    onClick: () -> Unit
) {
    val colors = if (danger) {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    } else {
        CardDefaults.cardColors()
    }

    Card(
        colors = colors,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
