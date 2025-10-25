package vn.kotlinproject.uth_smarttasks.ui.screen.auth.password

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import vn.kotlinproject.uth_smarttasks.ui.vm.rememberForgotPasswordVM
import vn.kotlinproject.uth_smarttasks.ui.vm.ForgotPasswordViewModel

@Composable
fun FPConfirmScreen(
    email: String,
    code: String,
    password: String,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    vm: ForgotPasswordViewModel = rememberForgotPasswordVM()
) {
    FPScaffold(title = "Confirm", subtitle = "We are here to help you!", onBack = onBack) {
        OutlinedTextField(value = email, onValueChange = {}, enabled = false, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = code, onValueChange = {}, enabled = false, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = "*".repeat(password.length), onValueChange = {}, enabled = false, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        Button(
            enabled = !vm.loading,
            onClick = { vm.setNewPassword(email, password) { onSubmit() } },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Submit") }

        if (vm.loading) { Spacer(Modifier.height(12.dp)); LinearProgressIndicator(Modifier.fillMaxWidth()) }
        vm.error?.let { Spacer(Modifier.height(8.dp)); Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
