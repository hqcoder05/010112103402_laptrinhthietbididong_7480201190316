package vn.kotlinproject.btvn.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FacebookLoginHelper {

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private var isRegistered = false

    /**
     * Gọi 1 lần (ví dụ trong LaunchedEffect) để gắn callback
     */
    fun register(
        onToken: (String) -> Unit,
        onCancel: () -> Unit = {},
        onErr: (Exception) -> Unit = {}
    ) {
        if (isRegistered) return
        isRegistered = true

        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(res: LoginResult) {
                    onToken(res.accessToken.token)
                }

                override fun onCancel() {
                    onCancel()
                }

                override fun onError(e: FacebookException) {
                    Log.e("FB_LOGIN", "Facebook login error", e)
                    onErr(e)
                }
            }
        )
    }

    /**
     * Gọi khi bấm nút “Đăng nhập với Facebook”
     */
    fun login(activity: Activity) {
        LoginManager.getInstance()
            .logInWithReadPermissions(activity, listOf("email"))
    }

    /**
     * Forward từ Activity -> Facebook SDK
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
