package net.kep.dc_guide.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Settings

object NavBarItems {
    val items = listOf(
        BarItem(
            title = "Теория",
            icon = Icons.Default.Book,
            route = "guide"
        ),
        BarItem(
            title = "Калькулятор",
            icon = Icons.Default.Calculate,
            route = "calculator"
        ),
        BarItem(
            title = "Настройки",
            icon = Icons.Default.Settings,
            route = "settings"
        )
    )
}

