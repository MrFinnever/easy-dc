package net.kep.easy_dc.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.kep.easy_dc.data.settings.SettingsData
import net.kep.easy_dc.data.settings.SettingsManager
import net.kep.easy_dc.ui.navigation.CalcNavHost
import net.kep.easy_dc.ui.navigation.NavBar
import net.kep.easy_dc.ui.navigation.NavRoutes

@Composable
fun MainScreen(
    settingsData: SettingsData,
    settingsManager: SettingsManager
) {
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
            composable(NavRoutes.Settings.route) { SettingsScreen(
                settingsData = settingsData, settingsManager = settingsManager
            ) }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val context = LocalContext.current
    MainScreen(
        settingsData = SettingsData(),
        settingsManager = SettingsManager(context.applicationContext)
    )
}