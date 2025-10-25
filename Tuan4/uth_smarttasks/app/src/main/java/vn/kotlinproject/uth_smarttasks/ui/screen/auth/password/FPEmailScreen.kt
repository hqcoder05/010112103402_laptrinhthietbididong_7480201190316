package vn.kotlinproject.uth_smarttasks.ui.screen.auth.password

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vn.kotlinproject.uth_smarttasks.ui.vm.ForgotPasswordViewModel
import vn.kotlinproject.uth_smarttasks.ui.vm.rememberForgotPasswordVM

@Composable
fun FPEmailScreen(
    onNext: (String) -> Unit,
    vm: ForgotPasswordViewModel = rememberForgotPasswordVM()
) {
    var email by rememberSaveable { mutableStateOf("") }
    val enabled = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    FPScaffold(
        title = "Forget Password?",
        subtitle = "Enter your Email, we will send you a verification code."
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Your Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(Modifier.height(20.dp))

        Button(
            enabled = enabled && !vm.loading,
            onClick = {
                // DEV: tạm bỏ gọi backend để tránh NOT_FOUND, đi thẳng sang OTP
                onNext(email)

                // PROD/EMULATOR (bật lại khi có backend):
                // vm.sendOtp(email) { onNext(email) }
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
