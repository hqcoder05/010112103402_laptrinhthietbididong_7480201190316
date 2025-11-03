package vn.kotlinproject.btvn.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import vn.kotlinproject.btvn.auth.AuthViewModel
import vn.kotlinproject.btvn.auth.GoogleSignInHelper

@Composable
fun AuthScreen(
    vm: AuthViewModel,
    onLoggedIn: () -> Unit
) {
    val activity = LocalContext.current as Activity
    val googleHelper = remember(activity) { GoogleSignInHelper(activity) }

    val loading by vm.loading.collectAsState()
    val errorVm by vm.error.collectAsState()
    val snack = remember { SnackbarHostState() }

    var selectedTab by remember { mutableStateOf(0) }

    var loginEmail by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }

    var regEmail by remember { mutableStateOf("") }
    var regPassword by remember { mutableStateOf("") }

    var localErr by remember { mutableStateOf<String?>(null) }

    // Google launcher
    val googleLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken ?: error("Không lấy được idToken")
            vm.loginGoogle(idToken, onLoggedIn)
        } catch (e: ApiException) {
            localErr = when (e.statusCode) {
                CommonStatusCodes.CANCELED, 12501 -> "Bạn đã huỷ chọn tài khoản Google"
                12500 -> "Đăng nhập Google thất bại (SIGN_IN_FAILED)"
                10 -> "DEVELOPER_ERROR (10): Sai SHA-1/SHA-256 hoặc default_web_client_id"
                7 -> "Lỗi mạng. Kiểm tra Internet"
                else -> "Google Sign-In lỗi: ${e.statusCode}"
            }
        } catch (e: Throwable) {
            localErr = e.message ?: "Lỗi Google Sign-In"
        }
    }

    LaunchedEffect(errorVm, localErr) {
        (errorVm ?: localErr)?.let {
            snack.showSnackbar(it)
            localErr = null
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snack) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp)
        ) {
            Text("Xác thực", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))

            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Đăng nhập") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Đăng ký") }
                )
            }

            Spacer(Modifier.height(16.dp))

            if (selectedTab == 0) {
                // LOGIN
                OutlinedTextField(
                    value = loginEmail,
                    onValueChange = { loginEmail = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = loginPassword,
                    onValueChange = { loginPassword = it },
                    label = { Text("Mật khẩu") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { vm.loginEmail(loginEmail, loginPassword, onLoggedIn) },
                    enabled = !loading && loginEmail.isNotBlank() && loginPassword.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Đăng nhập bằng Email")
                }

                TextButton(
                    onClick = { vm.resetPassword(loginEmail) },
                    enabled = !loading && loginEmail.isNotBlank()
                ) {
                    Text("Quên mật khẩu")
                }
            } else {
                // REGISTER
                OutlinedTextField(
                    value = regEmail,
                    onValueChange = { regEmail = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = regPassword,
                    onValueChange = { regPassword = it },
                    label = { Text("Mật khẩu (>=6)") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        vm.registerEmail(regEmail, regPassword) {
                            selectedTab = 0
                        }
                    },
                    enabled = !loading && regEmail.isNotBlank() && regPassword.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Đăng ký & gửi email xác minh")
                }

                Text(
                    "Xác minh email xong rồi quay lại tab Đăng nhập.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(20.dp))
            Text("Hoặc", modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { googleLauncher.launch(googleHelper.intent()) },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Đăng nhập với Google")
            }

            if (loading) {
                Spacer(Modifier.height(16.dp))
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}
