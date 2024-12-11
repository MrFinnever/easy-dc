package net.kep.easy_dc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.kep.easy_dc.data.settings.SettingsManager
import net.kep.easy_dc.ui.navigation.CalcNavHost
import net.kep.easy_dc.ui.navigation.NavBar
import net.kep.easy_dc.ui.navigation.NavRoutes
import net.kep.easy_dc.ui.theme.LocalColors
import net.kep.easy_dc.ui.viewmodel.SettingsViewModel

@Composable
fun MainScreen(
    settingsViewModel: SettingsViewModel,
    settingsManager: SettingsManager
) {
    val mainNavCon = rememberNavController()

    Scaffold(
        bottomBar = { NavBar(navController = mainNavCon) }
    ) {
        NavHost(
            navController = mainNavCon,
            startDestination = NavRoutes.Calculator.route,
            modifier = Modifier.padding(it).background(LocalColors.current.background)
        ) {
            composable(NavRoutes.Guide.route) { GuideScreen(settingsViewModel) }
            composable(NavRoutes.Calculator.route) {
                CalcNavHost(mainNavCon)
            }
            composable(NavRoutes.Settings.route) {
                SettingsScreen(
                    settingsViewModel = settingsViewModel,
                    settingsManager = settingsManager
                )
            }
        }
    }
}