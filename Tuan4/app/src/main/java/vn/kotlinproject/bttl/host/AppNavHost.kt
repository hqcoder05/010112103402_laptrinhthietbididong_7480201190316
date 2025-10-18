package vn.kotlinproject.bttl.host

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.kotlinproject.bttl.screen.DefaultAndLazyScreen
import vn.kotlinproject.bttl.screen.DefaultColumnScreen
import vn.kotlinproject.bttl.screen.LazyColumnScreen

object RoutesBttl {
    const val DefaultAndLazy = "default_and_lazy"
    const val DefaultColumn = "default_column"
    const val LazyColumn = "lazy_column"
}

@Composable
fun AppNavHostBttl() {
    val nav = rememberNavController()
    NavHost(
        navController = nav,
        startDestination = RoutesBttl.DefaultAndLazy
    ) {
        composable(RoutesBttl.DefaultAndLazy) {
            DefaultAndLazyScreen(
                onBack = { /* nếu là màn đầu thì không cần back */ },
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
