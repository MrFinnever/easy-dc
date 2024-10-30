package net.kep.dc_guide.ui.navigation

sealed class NavRoutes(val route: String) {
    object Guide : NavRoutes(route = "guide")
    object Greeting : NavRoutes(route = "guide")
    object Calculator : NavRoutes(route ="calculator")
    object Help : NavRoutes(route ="help")
    object Result : NavRoutes(route ="result")
    object Solution : NavRoutes(route ="solution")
    object Settings : NavRoutes(route ="settings")
}