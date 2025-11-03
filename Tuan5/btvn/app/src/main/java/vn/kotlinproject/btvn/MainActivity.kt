package vn.kotlinproject.btvn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.google.firebase.FirebaseApp
import vn.kotlinproject.btvn.auth.AuthViewModel
import vn.kotlinproject.btvn.ui.AuthScreen
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            MaterialTheme {
                val authVm = remember { AuthViewModel() }
                AppContent(authVm)
            }
        }
    }
}

@Composable
fun AppContent(viewModel: AuthViewModel) {
    var isLoggedIn by remember { mutableStateOf(viewModel.user() != null) }

    if (isLoggedIn) {
        HomeScreen {
            viewModel.signOut()
            isLoggedIn = false
        }
    } else {
        AuthScreen(
            vm = viewModel,
            onLoggedIn = { isLoggedIn = true }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Trang chủ", style = MaterialTheme.typography.headlineSmall) }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🎉 Đăng nhập thành công!", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Đăng xuất")
            }
        }
    }
}
