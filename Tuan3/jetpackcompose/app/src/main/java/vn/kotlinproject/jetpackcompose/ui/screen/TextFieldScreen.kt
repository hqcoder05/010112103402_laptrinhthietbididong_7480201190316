package vn.kotlinproject.jetpackcompose.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(onBack: () -> Unit) {
    var text by rememberSaveable { mutableStateOf("") } // giữ state qua xoay màn hình

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TextField") },
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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },                // cập nhật ngay lập tức
                label = { Text("Nhập gì cũng được") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Hiển thị “live”
            Text(
                text = if (text.isEmpty()) "Ở đây sẽ hiển thị nội dung bạn gõ"
                else "Bạn gõ: $text",
                style = MaterialTheme.typography.bodyLarge,
                color = if (text.isEmpty()) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.primary
            )
        }
    }
}
