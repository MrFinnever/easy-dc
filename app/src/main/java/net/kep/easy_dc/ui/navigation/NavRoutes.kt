package net.kep.easy_dc.ui.navigation

sealed class NavRoutes(val route: String) {
    object Guide : NavRoutes(route = "guide")
    object Greeting : NavRoutes(route = "guide")
    object Calculator : NavRoutes(route ="calculator")
    object Help : NavRoutes(route ="help")
    object Result : NavRoutes(route ="result")
    object Settings : NavRoutes(route ="settings")
}