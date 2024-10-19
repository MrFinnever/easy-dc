package net.kep.dc_guide.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import net.kep.dc_guide.R

sealed class NavRoutes(val route: String) {
    object Guide : NavRoutes(route = "guide")
    object Greeting : NavRoutes(route = "guide")
    object Calculator : NavRoutes(route ="calculator")
    object Settings : NavRoutes(route ="settings")
}