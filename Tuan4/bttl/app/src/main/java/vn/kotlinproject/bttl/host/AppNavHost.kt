package vn.kotlinproject.bttl.host

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.kotlinproject.bttl.screen.DefaultAndLazyScreen
import vn.kotlinproject.bttl.screen.DefaultColumnScreen
import vn.kotlinproject.bttl.screen.LazyColumnScreen
import vn.kotlinproject.bttl.screen.IntroScreen   // dùng IntroScreen sẵn có, tham số onReady

object RoutesBttl {
    const val Intro = "intro"
    const val DefaultAndLazy = "default_and_lazy"
    const val DefaultColumn = "default_column"
    const val LazyColumn = "lazy_column"
}

@Composable
fun AppNavHostBttl() {
    val nav = rememberNavController()
    NavHost(
        navController = nav,
        startDestination = RoutesBttl.Intro
    ) {
        composable(RoutesBttl.Intro) {
            IntroScreen(
                onReady = {                         // 🔁 đổi onStart -> onReady
                    nav.navigate(RoutesBttl.DefaultAndLazy) {
                        popUpTo(RoutesBttl.Intro) { inclusive = true }
                    }
                }
            )
        }

        composable(RoutesBttl.DefaultAndLazy) {
            DefaultAndLazyScreen(
                onBack = { /* root, không back */ },
                onOpenDefault = { nav.navigate(RoutesBttl.DefaultColumn) },
                onOpenLazy = { nav.navigate(RoutesBttl.LazyColumn) }
            )
        }

        composable(RoutesBttl.DefaultColumn) {
            DefaultColumnScreen(onBack = { nav.popBackStack() })
        }

        composable(RoutesBttl.LazyColumn) {
            LazyColumnScreen(onBack = { nav.popBackStack() })
        }
    }
}
