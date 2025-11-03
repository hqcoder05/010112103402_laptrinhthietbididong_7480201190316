package vn.kotlinproject.thuchanh1.ui.features.books

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.kotlinproject.thuchanh1.domain.model.Book

private val BlueButton   = Color(0xFF175EB6)
private val BlueOn       = Color(0xFFFFFFFF)
private val ListBgGray   = Color(0xFFE7E7EA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(vm: BookViewModel = viewModel(factory = BookViewModel.factory())) {
    val books by vm.books.collectAsState()
    var title by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Danh sách Sách") }) }
    ) { p ->
        Column(Modifier.padding(p).padding(16.dp)) {
            Text("Thêm sách", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Tiêu đề sách") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { vm.addBook(title); title = "" },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueButton, contentColor = BlueOn),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Thêm") }
            }

            Spacer(Modifier.height(16.dp))
            Text("Danh sách", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ListBgGray)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (books.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Chưa có sách nào, hãy thêm mới.", fontWeight = FontWeight.SemiBold)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(books, key = Book::id) { b ->
                            BookPill(b)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookPill(b: Book) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        shadowElevation = 6.dp,
        tonalElevation = 0.dp,
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val badgeColor = if (b.isAvailable) Color(0xFF2E7D32) else Color(0xFFD32F2F)
            Surface(
                color = badgeColor.copy(alpha = .12f),
                contentColor = badgeColor,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    if (b.isAvailable) "Có sẵn" else "Đang mượn",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(b.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text("ID: ${b.id}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}
