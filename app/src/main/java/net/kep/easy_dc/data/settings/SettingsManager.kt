package net.kep.easy_dc.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SettingsManager(private val context: Context) {
    companion object SettingsKeys {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val FONT_SIZE_KEY = intPreferencesKey("font_size")
    }


    //==================== Get All Settings ====================//

    val getSettings: Flow<SettingsData> = context.dataStore.data
        .map { preferences ->
            SettingsData(
                themeMode = preferences[THEME_MODE_KEY] ?: "system",
                language = preferences[LANGUAGE_KEY] ?: "ru",
                fontSize = preferences[FONT_SIZE_KEY] ?: 3
            )
        }


    //==================== Set All Settings ====================//

    suspend fun saveSettings(settings: SettingsData) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = settings.themeMode
            preferences[LANGUAGE_KEY] = settings.language
            preferences[FONT_SIZE_KEY] = settings.fontSize
        }
    }
}