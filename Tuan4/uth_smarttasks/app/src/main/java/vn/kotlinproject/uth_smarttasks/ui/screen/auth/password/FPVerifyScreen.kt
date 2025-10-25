package vn.kotlinproject.uth_smarttasks.ui.screen.auth.verify

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import vn.kotlinproject.uth_smarttasks.ui.vm.rememberForgotPasswordVM
import vn.kotlinproject.uth_smarttasks.ui.vm.ForgotPasswordViewModel
import vn.kotlinproject.uth_smarttasks.ui.screen.auth.password.FPScaffold

@Composable
fun FPVerifyScreen(
    email: String,
    onNext: (String) -> Unit,
    onBack: () -> Unit,
    vm: ForgotPasswordViewModel = rememberForgotPasswordVM()
) {
    var code by rememberSaveable { mutableStateOf("") }
    val ok = code.length == 4 && code.all(Char::isDigit) // OTP 4 số (1234)

    FPScaffold(
        title = "Verify Code",
        subtitle = "Enter the 4-digit code we sent to $email",
        onBack = onBack
    ) {
        OutlinedTextField(
            value = code,
            onValueChange = { input ->
                val cleaned = input.filter { it.isDigit() }.take(4)
                if (cleaned != code) code = cleaned
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Enter 4-digit code") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            enabled = ok && !vm.loading,
            onClick = {
                if (code == "1234") {
                    // DEV: tạo session cục bộ và đi tiếp (không gọi backend)
                    vm.setLocalSession()
                    onNext(code)
                } else {
                    // PROD/EMULATOR: verify thật
                    vm.checkCode(email, code) { onNext(code) }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Next") }

        if (vm.loading) {
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }
        vm.error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
