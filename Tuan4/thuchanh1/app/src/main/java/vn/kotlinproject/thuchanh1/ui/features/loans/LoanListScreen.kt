package vn.kotlinproject.thuchanh1.ui.features.loans

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.kotlinproject.thuchanh1.domain.model.Loan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanListScreen(
    prefilledStudentId: String = "",
    vm: LoanViewModel = viewModel(factory = LoanViewModel.factory())
) {
    val currentStudentId by vm.studentId.collectAsState()
    val loans by vm.loans.collectAsState()

    // Nếu được truyền từ màn Students → set luôn
    LaunchedEffect(prefilledStudentId) {
        if (prefilledStudentId.isNotBlank()) vm.setStudent(prefilledStudentId)
    }

    val hasPrefilled = prefilledStudentId.isNotBlank()
    var studentIdInput by remember { mutableStateOf(prefilledStudentId) }
    var bookIdInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (currentStudentId.isNotBlank())
                        Text("Mượn / Trả sách — SV: $currentStudentId")
                    else
                        Text("Mượn / Trả sách")
                }
            )
        }
    ) { p ->
        Column(Modifier.padding(p).padding(16.dp)) {

            // --- Chọn sinh viên ---
            if (!hasPrefilled) {
                OutlinedTextField(
                    value = studentIdInput,
                    onValueChange = { studentIdInput = it },
                    label = { Text("Student ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(6.dp))
                Button(onClick = { vm.setStudent(studentIdInput.trim()) }) {
                    Text("Tải lịch sử")
                }
                Spacer(Modifier.height(12.dp))
                Divider()
                Spacer(Modifier.height(12.dp))
            } else if (currentStudentId.isNotBlank()) {
                AssistChip(onClick = {}, label = { Text("Sinh viên: $currentStudentId") })
                Spacer(Modifier.height(12.dp))
                Divider()
                Spacer(Modifier.height(12.dp))
            }

            // --- Khu mượn sách (chỉ bật khi đã có SV được chọn) ---
            Text("Mượn sách", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = bookIdInput,
                onValueChange = { bookIdInput = it },
                label = { Text("Book ID") },
                modifier = Modifier.fillMaxWidth(),
                enabled = currentStudentId.isNotBlank()
            )
            Spacer(Modifier.height(6.dp))
            Button(
                enabled = currentStudentId.isNotBlank() && bookIdInput.isNotBlank(),
                onClick = {
                    vm.borrow(bookIdInput.trim())
                    bookIdInput = ""
                }
            ) { Text("Xác nhận mượn") }

            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            // --- Lịch sử mượn của SV đã chọn ---
            Text("Lịch sử mượn", style = MaterialTheme.typography.titleMedium)

            if (currentStudentId.isBlank()) {
                Spacer(Modifier.height(8.dp))
                Text("Hãy chọn sinh viên từ màn 'Sinh viên' hoặc nhập Student ID ở trên.")
            } else {
                LazyColumn {
                    items(loans, key = Loan::id) { l ->
                        ListItem(
                            headlineContent = { Text("Book: ${l.bookId}") },
                            supportingContent = {
                                val status = if (l.returnedAt == null) "Đang mượn" else "Đã trả"
                                Text("Loan #${l.id.take(8)} • $status")
                            },
                            trailingContent = {
                                if (l.returnedAt == null) {
                                    TextButton(onClick = { vm.returnLoan(l.id, l.bookId) }) {
                                        Text("Trả sách")
                                    }
                                }
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
