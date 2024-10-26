package net.kep.dc_guide.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.kep.dc_guide.ui.navigation.CalcNavHost
import net.kep.dc_guide.ui.navigation.NavBar
import net.kep.dc_guide.ui.navigation.NavRoutes

@Composable
fun MainScreen() {
    val mainNavCon = rememberNavController()

    Scaffold(
        bottomBar = { NavBar(navController = mainNavCon) }
    ) {
        NavHost(
            navController = mainNavCon,
            startDestination = NavRoutes.Calculator.route,
            modifier = Modifier.padding(it)
        ) {
            composable(NavRoutes.Guide.route) { GuideScreen() }
            composable(NavRoutes.Calculator.route) {
                CalcNavHost(mainNavCon)
            }
            composable(NavRoutes.Settings.route) { SettingsScreen() }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}