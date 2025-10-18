package vn.kotlinproject.bttl.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyColumnScreen(onBack: () -> Unit) {
    val total = 1_000_000
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var jumpText by rememberSaveable { mutableStateOf("") }
    val canJump = jumpText.toIntOrNull()?.let { it in 0 until total } == true

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LazyColumn (1,000,000 items)") },
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
        ) {
            // Thanh điều khiển: nhảy tới index bất kỳ
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = jumpText,
                    onValueChange = { jumpText = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Jump to index (0..${total - 1})") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    enabled = canJump,
                    onClick = {
                        val target = jumpText.toInt()
                        scope.launch {
                            // dùng animateScrollToItem nếu muốn cuộn mượt (nhưng chậm với index xa)
                            listState.scrollToItem(target)
                        }
                    }
                ) { Text("Go") }
            }

            Divider()

            // Danh sách lười (chỉ compose item đang hiển thị)
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // items(count) có hỗ trợ key; dùng key ổn định giúp recyc/animate tốt hơn
                items(
                    count = total,
                    key = { index -> index },                 // key ổn định
                    itemContent = { index ->
                        ItemRow(index = index)
                    }
                )
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
