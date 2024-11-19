package net.kep.easy_dc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.kep.easy_dc.data.settings.SettingsData
import net.kep.easy_dc.data.settings.SettingsManager
import net.kep.easy_dc.ui.screens.MainScreen
import net.kep.easy_dc.ui.theme.EasyDCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsManager = SettingsManager(this)

        setContent {
            EasyDCTheme {
                val settingsState = remember { mutableStateOf(SettingsData()) }

                LaunchedEffect(key1 = true) {
                    settingsManager.getSettings.collect { settings ->
                        settingsState.value = settings
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        settingsData = settingsState.value,
                        settingsManager = settingsManager
                    )
                }
            }
        }
    }
}








