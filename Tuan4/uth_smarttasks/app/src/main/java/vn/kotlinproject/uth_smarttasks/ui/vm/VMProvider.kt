package vn.kotlinproject.uth_smarttasks.ui.vm

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.kotlinproject.uth_smarttasks.data.repo.AuthRepository
import vn.kotlinproject.uth_smarttasks.data.repo.FirebaseAuthRepository

class ForgotPasswordViewModelFactory(
    private val repo: AuthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForgotPasswordViewModel(repo) as T
    }
}

@Composable
fun rememberForgotPasswordVM(): ForgotPasswordViewModel {
    // 👇 Scope theo Activity để mọi màn dùng chung 1 VM
    val activity = LocalContext.current as ComponentActivity
    return viewModel(
        viewModelStoreOwner = activity,
        key = "ForgotPasswordVM",
        factory = ForgotPasswordViewModelFactory(FirebaseAuthRepository())
    )
}
