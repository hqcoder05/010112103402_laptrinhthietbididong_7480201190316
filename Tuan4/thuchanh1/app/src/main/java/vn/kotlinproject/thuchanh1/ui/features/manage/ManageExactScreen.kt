package vn.kotlinproject.thuchanh1.ui.features.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.kotlinproject.thuchanh1.domain.model.Book

private val BlueButton   = Color(0xFF175EB6)
private val BlueButtonOn = Color(0xFFFFFFFF)
private val ListBgGray   = Color(0xFFE7E7EA)
private val TickBg       = Color(0xFFD34A67)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageExactScreen(vm: ManageViewModel = viewModel(factory = ManageViewModel.factory())) {
    val ui by vm.ui.collectAsState()
    val available by vm.availableBooks.collectAsState()

    var input by remember { mutableStateOf(ui.studentName) }
    var showPicker by remember { mutableStateOf(false) }

    Scaffold { p ->
        Column(
            Modifier.padding(p).fillMaxSize().padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Tiêu đề
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text("Hệ thống", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                Text("Quản lý Thư viện", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.height(18.dp))

            Text("Sinh viên", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { vm.changeStudentByName(input) },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueButton, contentColor = BlueButtonOn),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) { Text("Thay đổi") }
            }

            Spacer(Modifier.height(18.dp))
            Text("Danh sách sách", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ListBgGray)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when {
                    ui.message != null && ui.books.isEmpty() -> {
                        CenterMsg(ui.message!!)
                    }
                    ui.books.isEmpty() -> {
                        CenterMsg(
                            "Bạn chưa mượn quyển sách nào\nNhấn ‘Thêm’ để bắt đầu hành trình đọc sách!"
                        )
                    }
                    else -> {
                        ui.books.forEach { BookPill(it) }
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            Button(
                onClick = { showPicker = true },
                enabled = ui.studentName.isNotBlank() && available.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = BlueButton, contentColor = BlueButtonOn),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.6f).height(48.dp)
            ) { Text("Thêm", fontSize = 18.sp, fontWeight = FontWeight.SemiBold) }
        }
    }

    // ---------- Bottom Sheet chọn sách để mượn ----------
    if (showPicker) {
        ModalBottomSheet(onDismissRequest = { showPicker = false }) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Chọn sách để mượn", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                if (available.isEmpty()) {
                    Text("Hiện không có sách nào trống.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    available.forEach { b: Book ->
                        ListItem(
                            headlineContent = { Text(b.title) },
                            supportingContent = { Text(if (b.isAvailable) "Có sẵn" else "Đang mượn") },
                            trailingContent = {
                                TextButton(
                                    enabled = b.isAvailable,
                                    onClick = {
                                        vm.borrowSelected(b.id) {
                                            // đóng sheet sau khi mượn xong
                                            showPicker = false
                                        }
                                    }
                                ) { Text("Mượn") }
                            }
                        )
                        Divider()
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun CenterMsg(msg: String) {
    Column(
        Modifier.fillMaxWidth().padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(msg, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
    }
}

@Composable
private fun BookPill(item: BorrowedBookUI) {
    Surface(shape = RoundedCornerShape(22.dp), shadowElevation = 6.dp, tonalElevation = 0.dp, color = Color.White) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(22.dp).clip(RoundedCornerShape(6.dp)).background(TickBg),
                contentAlignment = Alignment.Center
            ) { if (item.checked) Text("✓", color = Color.White) }
            Spacer(Modifier.width(10.dp))
            Text(item.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
