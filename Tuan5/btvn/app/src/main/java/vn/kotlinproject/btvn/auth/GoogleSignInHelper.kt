package vn.kotlinproject.btvn.auth

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Tasks
import vn.kotlinproject.btvn.R

/**
 * Helper tạo Intent đăng nhập Google và trích xuất idToken từ kết quả.
 * YÊU CẦU: strings.xml có default_web_client_id (Web client ID từ google-services.json)
 */
class GoogleSignInHelper(private val activity: Activity) {
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val client = GoogleSignIn.getClient(activity, gso)

    /** Mở màn chọn tài khoản Google */
    fun intent(): Intent = client.signInIntent

    /** Lấy idToken từ onActivityResult */
    fun extractIdToken(data: Intent?): String {
        val acct = Tasks.await(GoogleSignIn.getSignedInAccountFromIntent(data))
        return acct.idToken ?: error("No idToken from Google")
    }
}
