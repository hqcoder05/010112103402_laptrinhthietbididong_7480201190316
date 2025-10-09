package vn.kotlinproject.tuan2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NumberScreen()
                }
            }
        }
    }
}

@Composable
fun NumberScreen() {
    // --- STATE ---
    var input by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(listOf<Int>()) }
    var error by remember { mutableStateOf<String?>(null) }

    // --- MÀU SẮC CHÍNH ---
    val redItem = Color(0xFFE53935)   // màu nút danh sách
    val errorRed = Color(0xFFD32F2F)  // màu chữ lỗi

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TIÊU ĐỀ “Thực hành 02”
        Text(
            text = "Thực hành 02",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(24.dp))

        // HÀNG NHẬP LIỆU + NÚT TẠO (giống bố cục trong hình)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    error = null                    // xoá lỗi khi đang gõ
                },
                placeholder = { Text("Nhập vào số lượng") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = {
                    val n = input.trim().toIntOrNull()
                    if (n != null && n > 0) {
                        items = (1..n).toList()     // tạo danh sách 1..n
                        error = null
                    } else {
                        items = emptyList()
                        error = "Dữ liệu bạn nhập không hợp lệ"
                    }
                },
                enabled = input.isNotBlank(),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.height(48.dp)
            ) { Text("Tạo") }
        }

        // THÔNG BÁO LỖI
        if (error != null) {
            Spacer(Modifier.height(12.dp))
            Text(text = error!!, color = errorRed, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(20.dp))

        // DANH SÁCH NÚT ĐỎ BO TRÒN (full width)
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { num ->
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = redItem,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(num.toString(), style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberScreenPreview() {
    MaterialTheme { NumberScreen() }
}
