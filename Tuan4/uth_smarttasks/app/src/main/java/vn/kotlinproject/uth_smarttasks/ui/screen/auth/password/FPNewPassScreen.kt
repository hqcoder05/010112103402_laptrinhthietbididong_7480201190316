package vn.kotlinproject.uth_smarttasks.ui.screen.auth.password

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun FPNewPassScreen(
    onNext: (String) -> Unit,
    onBack: () -> Unit
) {
    var p1 by rememberSaveable { mutableStateOf("") }
    var p2 by rememberSaveable { mutableStateOf("") }
    val ok = p1.length >= 6 && p1 == p2

    FPScaffold(
        title = "Create new password",
        subtitle = "Your new password must be different from previously used password",
        onBack = onBack
    ) {
        OutlinedTextField(
            value = p1, onValueChange = { p1 = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, placeholder = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = p2, onValueChange = { p2 = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, placeholder = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(20.dp))
        Button(
            enabled = ok,
            onClick = { onNext(p1) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Next") }

        if (!ok) { Spacer(Modifier.height(8.dp)); Text("Mật khẩu ≥ 6 ký tự và khớp nhau", color = MaterialTheme.colorScheme.onSurfaceVariant) }
    }
}
