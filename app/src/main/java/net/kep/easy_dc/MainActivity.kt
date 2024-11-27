package net.kep.easy_dc

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import net.kep.easy_dc.data.settings.SettingsManager
import net.kep.easy_dc.ui.screens.MainScreen
import net.kep.easy_dc.ui.theme.EasyDCTheme
import net.kep.easy_dc.ui.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsManager = SettingsManager(this.applicationContext)

        setContent {
            EasyDCTheme {
                val currentLocale = this@MainActivity.applicationContext.resources
                                                            .configuration.locales[0]

                val settingsViewModel = SettingsViewModel(settingsManager)
                val language by settingsViewModel.language.collectAsState()

                if (language == "null")
                    settingsViewModel.setSettings(currentLocale.language)





                Log.d("MainActivity", "language: ${settingsViewModel.language}")
                Log.d("MainActivity", "themeMode: ${settingsViewModel.themeMode}")
                Log.d("MainActivity", "fontSize: ${settingsViewModel.fontSize}")

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        settingsManager = settingsManager
                    )
                }
            }
        }
    }
}








