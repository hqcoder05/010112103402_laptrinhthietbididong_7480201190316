package vn.kotlinproject.thuchanh1.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomItem(val route: String, val label: String, val icon: ImageVector)

private val items = listOf(
    BottomItem(Routes.Manage,   "Quản lý",  Icons.Outlined.Home),
    BottomItem(Routes.Books,    "DS Sách",  Icons.Outlined.ListAlt),
    BottomItem(Routes.Students, "Sinh viên", Icons.Outlined.Person),
)

@Composable
fun LibraryBottomBar(
    nav: NavController,
    lockMockUI: Boolean = false
) {
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            val selected = currentRoute?.startsWith(item.route) == true
            val enabled = if (lockMockUI) item.route == Routes.Manage else true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected && enabled) {
                        nav.navigate(item.route) {
                            // Pop đến startDestination của graph gốc (KHÔNG thoát app)
                            popUpTo(nav.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                enabled = enabled,
                alwaysShowLabel = true
            )
        }
    }
}
