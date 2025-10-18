package vn.kotlinproject.jetpackcompose.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.kotlinproject.jetpackcompose.ui.screen.ComponentsListScreen
import vn.kotlinproject.jetpackcompose.ui.screen.IntroScreen
import vn.kotlinproject.jetpackcompose.ui.screen.TextDetailScreen
import vn.kotlinproject.jetpackcompose.ui.screen.ImagesScreen
import vn.kotlinproject.jetpackcompose.ui.screen.TextFieldScreen
import vn.kotlinproject.jetpackcompose.ui.screen.PasswordFieldScreen
import vn.kotlinproject.jetpackcompose.ui.screen.RowLayoutScreen
import vn.kotlinproject.jetpackcompose.ui.screen.ColumnLayoutScreen   // ⬅️ THÊM

object Routes {
    const val Intro = "intro"
    const val List = "components_list"
    const val TextDetail = "text_detail"
    const val Images = "images"
    const val TextField = "textfield"
    const val PasswordField = "password_field"
    const val ColumnLayout = "column_layout"
    const val RowLayout = "row_layout"
}

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.Intro) {

        composable(Routes.Intro) {
            IntroScreen(onReady = { nav.navigate(Routes.List) })
        }

        composable(Routes.List) {
            ComponentsListScreen(
                onItemClick = { key ->
                    when (key) {
                        "Text" -> nav.navigate(Routes.TextDetail)
                        "Image" -> nav.navigate(Routes.Images)
                        "TextField" -> nav.navigate(Routes.TextField)
                        "PasswordField" -> nav.navigate(Routes.PasswordField)
                        "Column" -> nav.navigate(Routes.ColumnLayout)
                        "Row" -> nav.navigate(Routes.RowLayout)
                    }
                }
            )
        }

        composable(Routes.TextDetail) { TextDetailScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.Images)     { ImagesScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.TextField)  { TextFieldScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.PasswordField) { PasswordFieldScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.ColumnLayout)  { ColumnLayoutScreen(onBack = { nav.popBackStack() }) } // ⬅️ THÊM
        composable(Routes.RowLayout)  { RowLayoutScreen(onBack = { nav.popBackStack() }) }
    }
}
