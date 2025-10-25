package vn.kotlinproject.uth_smarttasks.data.remote

import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object OtpApi {
    private val functions = Firebase.functions // nếu deploy region khác: Firebase.functions("asia-southeast1")

    suspend fun requestOtp(email: String) {
        functions.getHttpsCallable("requestOtp")
            .call(hashMapOf("email" to email))
            .await()
    }

    suspend fun verifyOtp(email: String, code: String): String {
        val res = functions.getHttpsCallable("verifyOtp")
            .call(hashMapOf("email" to email, "code" to code))
            .await()
        return res.data as? String ?: error("Invalid response")
    }

    suspend fun resetPassword(email: String, sessionId: String, newPassword: String) {
        functions.getHttpsCallable("resetPassword")
            .call(hashMapOf("email" to email, "sessionId" to sessionId, "newPassword" to newPassword))
            .await()
    }
}
