package vn.kotlinproject.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
                    AgeCheckScreen()
                }
            }
        }
    }
}

@Composable
fun AgeCheckScreen() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var resultColor by remember { mutableStateOf(Color.Transparent) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(24.dp))

        // Nhập họ tên
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                result = ""
                resultColor = Color.Transparent
            },
            label = { Text("Họ và tên") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Nhập tuổi
        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it.filter { c -> c.isDigit() } // Chỉ cho nhập số
                result = ""
                resultColor = Color.Transparent
            },
            label = { Text("Tuổi") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        // Nút kiểm tra
        Button(
            onClick = {
                if (name.isBlank() || age.isBlank()) {
                    result = "Vui lòng nhập đầy đủ thông tin!"
                    resultColor = Color.Red
                } else {
                    val ageInt = age.toIntOrNull()
                    if (ageInt == null) {
                        result = "Tuổi không hợp lệ!"
                        resultColor = Color.Red
                    } else {
                        resultColor = Color(0xFF1976D2)
                        result = when {
                            ageInt < 2 -> "$name là Em bé"
                            ageInt in 2..6 -> "$name là Trẻ em"
                            ageInt in 7..65 -> "$name là Người lớn"
                            else -> "$name là Người già"
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Kiểm tra", color = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        // Kết quả hiển thị
        if (result.isNotEmpty()) {
            Text(text = result, color = resultColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAgeCheckScreen() {
    MaterialTheme {
        AgeCheckScreen()
    }
}
