package net.kep.easy_dc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.kep.easy_dc.ui.screens.CalcScreen
import net.kep.easy_dc.ui.screens.GreetingScreen
import net.kep.easy_dc.ui.screens.HelpScreen
import net.kep.easy_dc.ui.screens.ResultScreen
import net.kep.easy_dc.ui.viewmodel.CalculatorViewModel

@Composable
fun CalcNavHost(
    mainNavCon: NavController
) {
    val calculatorViewModel: CalculatorViewModel = viewModel()
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
            CalcScreen(calculatorViewModel, calcNavCon)
        }
        composable(NavRoutes.Help.route) {
            HelpScreen(calcNavCon)
        }
        composable(NavRoutes.Result.route) {
            ResultScreen(calculatorViewModel, calcNavCon)
        }
    }
}