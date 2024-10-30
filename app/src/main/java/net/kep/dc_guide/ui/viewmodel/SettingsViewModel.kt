package net.kep.dc_guide.ui.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.kep.dc_guide.data.SettingsKeys
import net.kep.dc_guide.data.dataStore
import net.kep.dc_guide.ui.theme.ThemeMode

class SettingsViewModel(context: Context) {
    private val dataStore = context.dataStore

    val fontSizeFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SettingsKeys.FONT_SIZE] ?: 16 // значение по умолчанию
        }


    val themeModeFlow: Flow<ThemeMode> = dataStore.data
        .map { preferences ->
            when (preferences[SettingsKeys.THEME_MODE]) {
                "DARK" -> ThemeMode.DARK
                "LIGHT" -> ThemeMode.LIGHT
                else -> ThemeMode.SYSTEM
            }
        }

    suspend fun setThemeMode(context: Context, themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[SettingsKeys.THEME_MODE] = themeMode.mode
        }
    }
    suspend fun setFontSize(context: Context, fontSize: Int) {
        context.dataStore.edit { preferences ->
            preferences[SettingsKeys.FONT_SIZE] = fontSize
        }
    }
}


