package vn.kotlinproject.bttl.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultColumnScreen(onBack: () -> Unit) {
    val count = 1_000_000
    val scroll = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Column (non-lazy) — 1,000,000 items") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(count) { index ->
                    ItemRow(index)
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ItemRow(index: Int) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Item #$index", style = MaterialTheme.typography.titleMedium)
            Text("Value = $index", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
