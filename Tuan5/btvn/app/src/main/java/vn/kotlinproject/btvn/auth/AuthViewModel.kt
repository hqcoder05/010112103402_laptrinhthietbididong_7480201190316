package vn.kotlinproject.btvn.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- GOOGLE ---
    fun loginGoogle(idToken: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true; _error.value = null
            runCatching { repo.loginWithGoogle(idToken) }
                .onSuccess { onSuccess() }
                .onFailure { _error.value = it.message }
            _loading.value = false
        }
    }

    // --- FACEBOOK ---
    fun loginFacebook(accessToken: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true; _error.value = null
            runCatching { repo.loginWithFacebook(accessToken) }
                .onSuccess { onSuccess() }
                .onFailure { _error.value = it.message }
            _loading.value = false
        }
    }

    // --- EMAIL: LOGIN (bắt buộc đã verify) ---
    fun loginEmail(rawEmail: String, rawPassword: String, onSuccess: () -> Unit) {
        val email = rawEmail.trim()
        val password = rawPassword.trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _error.value = "Email không hợp lệ"
            return
        }
        if (password.isEmpty()) {
            _error.value = "Vui lòng nhập mật khẩu"
            return
        }

        viewModelScope.launch {
            _loading.value = true; _error.value = null
            try {
                repo.loginWithEmail(email, password)
                val user = repo.currentUser()
                if (user?.isEmailVerified == true) {
                    onSuccess()
                } else {
                    repo.signOut()
                    _error.value = "Email chưa được xác minh. Vui lòng mở email và bấm link."
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _error.value = "Email hoặc mật khẩu không đúng"
            } catch (e: FirebaseAuthInvalidUserException) {
                _error.value = "Tài khoản không tồn tại. Hãy đăng ký trước."
            } catch (e: Exception) {
                _error.value = e.message ?: "Đăng nhập thất bại"
            }
            _loading.value = false
        }
    }

    // --- EMAIL: REGISTER (gửi mail verify + signOut) ---
    fun registerEmail(rawEmail: String, rawPassword: String, onSent: () -> Unit) {
        val email = rawEmail.trim()
        val password = rawPassword.trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _error.value = "Email không hợp lệ"
            return
        }
        if (password.length < 6) {
            _error.value = "Mật khẩu phải >= 6 ký tự"
            return
        }

        viewModelScope.launch {
            _loading.value = true; _error.value = null
            runCatching { repo.registerWithEmail(email, password) }
                .onSuccess { onSent() }
                .onFailure { _error.value = it.message }
            _loading.value = false
        }
    }

    // --- EMAIL: RESET ---
    fun resetPassword(rawEmail: String) {
        val email = rawEmail.trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _error.value = "Email không hợp lệ"
            return
        }
        viewModelScope.launch {
            _loading.value = true; _error.value = null
            runCatching { repo.sendResetPassword(email) }
                .onFailure { _error.value = it.message }
            _loading.value = false
        }
    }

    fun user() = repo.currentUser()
    fun signOut() = repo.signOut()
}
