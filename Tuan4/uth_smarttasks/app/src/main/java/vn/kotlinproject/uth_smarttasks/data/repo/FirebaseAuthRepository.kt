package vn.kotlinproject.uth_smarttasks.data.repo

import vn.kotlinproject.uth_smarttasks.data.remote.OtpApi

class FirebaseAuthRepository : AuthRepository {
    override suspend fun requestOtp(email: String) = runCatching { OtpApi.requestOtp(email) }
    override suspend fun verifyOtp(email: String, code: String) = runCatching { OtpApi.verifyOtp(email, code) }
    override suspend fun resetPassword(email: String, sessionId: String, pass: String) =
        runCatching { OtpApi.resetPassword(email, sessionId, pass) }
}
