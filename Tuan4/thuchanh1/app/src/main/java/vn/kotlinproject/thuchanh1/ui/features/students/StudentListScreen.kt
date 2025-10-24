@file:OptIn(ExperimentalMaterial3Api::class)

package vn.kotlinproject.thuchanh1.ui.feature.students

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.kotlinproject.thuchanh1.domain.model.Student

@Composable
fun StudentListScreen(
    onOpenLoansForStudent: (studentId: String) -> Unit,
    vm: StudentViewModel = viewModel(factory = StudentViewModel.factory())
) {
    val students by vm.students.collectAsState()
    var name by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Danh sách Sinh viên") }) }) { p ->
        Column(Modifier.padding(p).padding(16.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tên sinh viên") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    vm.addStudent(name)
                    name = ""
                }
            ) { Text("Thêm sinh viên") }

            Divider(Modifier.padding(vertical = 12.dp))

            LazyColumn {
                items(students, key = Student::id) { s ->
                    ListItem(
                        headlineContent = {
                            // Bấm vào tên cũng mở Loans
                            Text(
                                text = s.name,
                                modifier = Modifier.clickable { onOpenLoansForStudent(s.id) }
                            )
                        },
                        supportingContent = { Text("ID: ${s.id}") },
                        // Toàn bộ dòng cũng bấm được
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenLoansForStudent(s.id) }
                    )
                    Divider()
                }
            }
        }
    }
}
