package net.kep.easy_dc

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import net.kep.easy_dc.data.settings.SettingsData
import net.kep.easy_dc.data.settings.SettingsManager
import net.kep.easy_dc.data.theme.enums.TextSize
import net.kep.easy_dc.ui.screens.MainScreen
import net.kep.easy_dc.ui.theme.EasyDCTheme
import net.kep.easy_dc.ui.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsManager = SettingsManager(this.applicationContext)
        val settingsViewModel = SettingsViewModel(settingsManager)


        setContent {

            val settings by settingsManager.getSettings.collectAsState(
                initial = SettingsData()
            )

            val themeMode by settingsViewModel.themeMode.collectAsState()
            Log.d("MainActivity", "themeMode: $themeMode")
            val fontSize by settingsViewModel.fontSize.collectAsState()
            Log.d("MainActivity", "fontSize: $fontSize")

            val isDark by settingsViewModel.themeMode.collectAsState(
                initial = isSystemInDarkTheme()
            )

            val isDarkTheme = when (isDark) {
                    "Dark" -> true
                    "Light" -> false
                    else -> isSystemInDarkTheme()
                }

            val textSize = when (settings.fontSize) {
                1 -> TextSize.Tiny
                2 -> TextSize.Small
                3 -> TextSize.Medium
                4 -> TextSize.Big
                5 -> TextSize.Large
                else -> TextSize.Medium
            }

            if (settings.language.isNotBlank() && settings.themeMode.isNotBlank()) {
                EasyDCTheme(
                    textSize = textSize,
                    darkTheme = isDarkTheme
                ) {
                    Log.d(
                        "MainActivity",
                        "language: ${settingsViewModel.language.collectAsState()}"
                    )
                    Log.d(
                        "MainActivity",
                        "themeMode: ${settingsViewModel.themeMode.collectAsState()}"
                    )
                    Log.d(
                        "MainActivity",
                        "fontSize: ${settingsViewModel.fontSize.collectAsState()}"
                    )

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen(
                            settingsViewModel = settingsViewModel,
                            settingsManager = settingsManager
                        )
                    }
                }
            }
        }
    }
}