package net.kep.dc_guide.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.kep.dc_guide.ui.screens.AdviceScreen
import net.kep.dc_guide.ui.screens.CalcScreen
import net.kep.dc_guide.ui.screens.GreetingScreen

@Composable
fun CalcNavHost(
    mainNavCon: NavController
) {
    val calcNavCon = rememberNavController()

    NavHost(
        navController = calcNavCon,
        startDestination = NavRoutes.Greeting.route
    ) {
        composable(NavRoutes.Greeting.route) {
            GreetingScreen(
                mainNavCon = mainNavCon,
                calcNavCon = calcNavCon
            )
        }
        composable(NavRoutes.Calculator.route) {
            CalcScreen(calcNavCon)
        }
        composable(NavRoutes.Advice.route) {
            AdviceScreen(calcNavCon)
        }
    }
}