package net.kep.easy_dc.ui.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Settings
import net.kep.easy_dc.R


object NavBarItems {
    fun getItems(context: Context): List<BarItem> {
         return listOf(
            BarItem(
                title = context.getString(R.string.guide),
                icon = Icons.Default.Book,
                route = "guide"
            ),
            BarItem(
                title = context.getString(R.string.calculator),
                icon = Icons.Default.Calculate,
                route = "calculator"
            ),
            BarItem(
                title = context.getString(R.string.settings),
                icon = Icons.Default.Settings,
                route = "settings"
            )
        )
    }
}

