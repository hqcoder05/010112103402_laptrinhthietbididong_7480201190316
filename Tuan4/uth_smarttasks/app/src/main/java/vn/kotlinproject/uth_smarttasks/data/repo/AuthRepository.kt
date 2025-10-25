package vn.kotlinproject.uth_smarttasks.data.repo

interface AuthRepository {
    suspend fun requestOtp(email: String): Result<Unit>
    suspend fun verifyOtp(email: String, code: String): Result<String> // sessionId
    suspend fun resetPassword(email: String, sessionId: String, pass: String): Result<Unit>
}
