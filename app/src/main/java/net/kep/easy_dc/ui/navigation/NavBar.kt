package net.kep.easy_dc.ui.navigation

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.kep.easy_dc.ui.theme.LocalColors
import net.kep.easy_dc.ui.theme.LocalTextStyles

@Composable
fun NavBar(navController: NavController) {
    NavigationBar(
        containerColor = LocalColors.current.surface,
        contentColor = LocalColors.current.onSurface
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        val context = LocalContext.current
        val navItems = NavBarItems.getItems(context)

        navItems.forEach { navItem ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LocalColors.current.onPrimary,
                    selectedTextColor = LocalColors.current.onSurface,
                    indicatorColor = LocalColors.current.primary,
                    disabledIconColor = LocalColors.current.onSurface,
                    unselectedIconColor = LocalColors.current.onSurface,
                    unselectedTextColor = LocalColors.current.onSurface,
                    disabledTextColor = LocalColors.current.onSurface
                ),
                selected = currentRoute == navItem.route,
                onClick = {
                    if (currentRoute != navItem.route)
                        navController.navigate(navItem.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId)
                        }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(
                        text = navItem.title,
                        fontSize = LocalTextStyles.current.navigationText.fontSize
                    )
                }
            )
        }
    }
}


@Preview(locale = "en")
@Composable
fun NavBarPreview() {
    val nav = rememberNavController()
    NavBar(navController = nav)
}

@Preview(locale = "ru")
@Composable
fun NavBarPreviewRu() {
    val nav = rememberNavController()
    NavBar(navController = nav)
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "en"
)
@Composable
fun NavBarPreviewDark() {
    val nav = rememberNavController()
    NavBar(navController = nav)
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "ru"
)
@Composable
fun NavBarPreviewDarkRu() {
    val nav = rememberNavController()
    NavBar(navController = nav)
}