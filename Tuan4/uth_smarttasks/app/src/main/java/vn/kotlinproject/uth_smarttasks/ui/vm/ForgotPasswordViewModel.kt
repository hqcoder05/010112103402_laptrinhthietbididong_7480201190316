package vn.kotlinproject.uth_smarttasks.ui.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.kotlinproject.uth_smarttasks.data.repo.AuthRepository
import vn.kotlinproject.uth_smarttasks.data.repo.FirebaseAuthRepository

class ForgotPasswordViewModel(
    private val repo: AuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    var loading by mutableStateOf(false); private set
    var error   by mutableStateOf<String?>(null); private set
    var sessionId by mutableStateOf<String?>(null); private set

    fun sendOtp(email: String, onOk: () -> Unit) = launchSafe {
        repo.requestOtp(email)
            .onSuccess { onOk() }
            .onFailure { error = it.message }
    }

    fun checkCode(email: String, code: String, onOk: () -> Unit) = launchSafe {
        repo.verifyOtp(email, code)
            .onSuccess { sid -> sessionId = sid; onOk() }
            .onFailure { error = it.message }
    }

    /** DEV: tạo session cục bộ để bỏ qua backend khi OTP = 1234 */
    fun setLocalSession() {
        sessionId = "DEV_LOCAL_SESSION"
    }

    fun setNewPassword(email: String, pass: String, onOk: () -> Unit) = launchSafe {
        val sid = sessionId ?: return@launchSafe run { error = "Invalid session" }
        // DEV bypass: nếu là session local thì không gọi server
        if (sid == "DEV_LOCAL_SESSION") {
            onOk()
            return@launchSafe
        }
        repo.resetPassword(email, sid, pass)
            .onSuccess { onOk() }
            .onFailure { error = it.message }
    }

    private fun launchSafe(block: suspend () -> Unit) = viewModelScope.launch {
        loading = true; error = null
        try { block() } finally { loading = false }
    }
}
