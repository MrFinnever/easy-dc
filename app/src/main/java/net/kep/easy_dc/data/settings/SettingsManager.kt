package net.kep.easy_dc.data.settings

import android.content.Context
import android.util.Log
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
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        val FONT_SIZE_KEY = intPreferencesKey("font_size")
    }


    //==================== Get All Settings ====================//

    val getSettings: Flow<SettingsData> = context.dataStore.data
        .map { preferences ->
            Log.d("SettingsManager:getSettings", "Saved language: ${preferences[LANGUAGE_KEY]}")
            Log.d("SettingsManager:getSettings", "Saved themeMode: ${preferences[THEME_MODE_KEY]}")
            Log.d("SettingsManager:getSettings", "Saved fontSize: ${preferences[FONT_SIZE_KEY]}")
            SettingsData(
                language = preferences[LANGUAGE_KEY] ?: "en",
                themeMode = preferences[THEME_MODE_KEY] ?: "System",
                fontSize = preferences[FONT_SIZE_KEY] ?: 3
            )
        }


    //==================== Set All Settings ====================//

    suspend fun saveSettings(settings: SettingsData) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = settings.language
            preferences[THEME_MODE_KEY] = settings.themeMode
            preferences[FONT_SIZE_KEY] = settings.fontSize
            Log.d("SettingsManager:saveSettings", "Successfully saved language: ${preferences[LANGUAGE_KEY]}")
            Log.d("SettingsManager:saveSettings", "Successfully saved themeMode: ${preferences[THEME_MODE_KEY]}")
            Log.d("SettingsManager:saveSettings", "Successfully saved fontSize: ${preferences[FONT_SIZE_KEY]}")
        }

    }
}