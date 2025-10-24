package vn.kotlinproject.thuchanh1.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import vn.kotlinproject.thuchanh1.ui.features.books.BookListScreen
import vn.kotlinproject.thuchanh1.ui.features.loans.LoanListScreen
import vn.kotlinproject.thuchanh1.ui.features.manage.ManageExactScreen
import vn.kotlinproject.thuchanh1.ui.feature.students.StudentListScreen

@Composable
fun AppRoot() {
    val nav = rememberNavController()

    Scaffold(
        bottomBar = { LibraryBottomBar(nav) }   // dùng CÙNG nav với NavHost
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Routes.Manage,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.Manage)   { ManageExactScreen() }
            composable(Routes.Books)    { BookListScreen() }
            composable(Routes.Students) {
                StudentListScreen(onOpenLoansForStudent = { id ->
                    nav.navigate(Routes.loans(id))   // 👈 mở Loans cho SV này
                })
            }
            composable(
                route = Routes.LoansPattern,
                arguments = listOf(
                    navArgument("studentId") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )
            ) { backStack ->
                val studentId = backStack.arguments?.getString("studentId").orEmpty()
                LoanListScreen(prefilledStudentId = studentId)
            }
        }
    }
}
