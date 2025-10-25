package vn.kotlinproject.uth_smarttasks.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.kotlinproject.uth_smarttasks.ui.screen.onboarding.OnboardingScreen
import vn.kotlinproject.uth_smarttasks.ui.screen.splash.SplashScreen

// Forgot Password screens
import vn.kotlinproject.uth_smarttasks.ui.screen.auth.password.FPEmailScreen
import vn.kotlinproject.uth_smarttasks.ui.screen.auth.verify.FPVerifyScreen
import vn.kotlinproject.uth_smarttasks.ui.screen.auth.password.FPNewPassScreen
import vn.kotlinproject.uth_smarttasks.ui.screen.auth.password.FPConfirmScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.Splash) {

        // Splash → Onboarding
        composable(Routes.Splash) {
            SplashScreen(
                onFinish = {
                    nav.navigate(Routes.Onboarding) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                }
            )
        }

        // Onboarding → thẳng vào Forgot Password (Email)
        composable(Routes.Onboarding) {
            OnboardingScreen(
                onSkip = { nav.navigate(Routes.FP_Email) { popUpTo(0) } },
                onDone = { nav.navigate(Routes.FP_Email) { popUpTo(0) } }
            )
        }

        // ===== Forgot Password flow =====

        // 1) Nhập Email
        composable(Routes.FP_Email) {
            FPEmailScreen(
                onNext = { email ->
                    nav.currentBackStackEntry?.savedStateHandle?.set("email", email)
                    nav.navigate(Routes.FP_Verify)
                }
            )
        }

        // 2) Nhập OTP
        composable(Routes.FP_Verify) {
            // lấy email từ entry trước (đã set ở FP_Email)
            val prevEmail = nav.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("email")
                .orEmpty()

            FPVerifyScreen(
                email = prevEmail,
                onNext = { code ->
                    nav.currentBackStackEntry?.savedStateHandle?.apply {
                        set("email", prevEmail)
                        set("code", code)
                    }
                    nav.navigate(Routes.FP_NewPass)
                },
                onBack = { nav.popBackStack() }
            )
        }

        // 3) Mật khẩu mới
        composable(Routes.FP_NewPass) {
            // 🔧 FIX: dùng remember khi gọi getBackStackEntry
            val verifyEntry = remember(nav) { nav.getBackStackEntry(Routes.FP_Verify) }
            val email = verifyEntry.savedStateHandle.get<String>("email").orEmpty()
            val code  = verifyEntry.savedStateHandle.get<String>("code").orEmpty()

            FPNewPassScreen(
                onNext = { pass ->
                    nav.currentBackStackEntry?.savedStateHandle?.apply {
                        set("email", email)
                        set("code", code)
                        set("pass", pass)
                    }
                    nav.navigate(Routes.FP_Confirm)
                },
                onBack = { nav.popBackStack() }
            )
        }

        // 4) Xác nhận & Submit
        composable(Routes.FP_Confirm) {
            // 🔧 FIX: dùng remember cho cả 2 entry nguồn
            val verifyEntry = remember(nav) { nav.getBackStackEntry(Routes.FP_Verify) }
            val newPassEntry = remember(nav) { nav.getBackStackEntry(Routes.FP_NewPass) }

            val email = verifyEntry.savedStateHandle.get<String>("email").orEmpty()
            val code  = verifyEntry.savedStateHandle.get<String>("code").orEmpty()
            val pass  = newPassEntry.savedStateHandle.get<String>("pass").orEmpty()

            FPConfirmScreen(
                email = email,
                code = code,
                password = pass,
                onSubmit = {
                    // Sau khi submit xong, quay về màn đầu flow (hoặc màn nào bạn muốn)
                    nav.navigate(Routes.FP_Email) { popUpTo(0) }
                },
                onBack = { nav.popBackStack() }
            )
        }
    }
}
