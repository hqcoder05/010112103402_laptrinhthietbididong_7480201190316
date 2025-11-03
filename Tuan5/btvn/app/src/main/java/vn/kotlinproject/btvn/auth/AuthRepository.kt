package vn.kotlinproject.btvn.auth

import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    // Google
    suspend fun loginWithGoogle(idToken: String) =
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null)).await()

    // Facebook
    suspend fun loginWithFacebook(accessToken: String) =
        auth.signInWithCredential(FacebookAuthProvider.getCredential(accessToken)).await()

    // Email: Đăng nhập
    suspend fun loginWithEmail(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).await()

    // Email: Đăng ký + gửi mail + signOut (bắt verify)
    suspend fun registerWithEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
        auth.currentUser?.sendEmailVerification()?.await()
        auth.signOut()
    }

    // Quên mật khẩu
    suspend fun sendResetPassword(email: String) =
        auth.sendPasswordResetEmail(email).await()

    fun currentUser() = auth.currentUser
    fun signOut() = auth.signOut()
}
