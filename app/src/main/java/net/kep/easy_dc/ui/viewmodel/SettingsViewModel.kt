package net.kep.easy_dc.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.kep.easy_dc.data.settings.SettingsData
import net.kep.easy_dc.data.settings.SettingsManager

class SettingsViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _themeMode = MutableStateFlow("System")
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    private val _fontSize = MutableStateFlow(3)
    val fontSize: StateFlow<Int> = _fontSize.asStateFlow()

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    init {
//        viewModelScope.launch {
//            val settings = settingsManager.getSettings.first() // Получаем настройки один раз
//            _language.value = settings.language
//            _themeMode.value = settings.themeMode
//            _fontSize.value = settings.fontSize
//            _isReady.value = true
//            Log.d("SettingsViewModel:init", "Init language: ${_language.value}")
//            Log.d("SettingsViewModel:init", "Init themeMode: ${_themeMode.value}")
//            Log.d("SettingsViewModel:init", "Init fontSize: ${_fontSize.value}")
//        }
        getSettings()
    }

    fun setLanguage(newLanguage: String) {


        viewModelScope.launch {
            settingsManager.saveSettings(
                SettingsData(
                    newLanguage,
                    _themeMode.value,
                    _fontSize.value
                )
            )
            _language.value = newLanguage
        }
    }

    fun setThemeMode(newThemeMode: String) {


        viewModelScope.launch {
            settingsManager.saveSettings(
                SettingsData(
                    _language.value,
                    newThemeMode,
                    _fontSize.value
                )
            )
            _themeMode.value = newThemeMode
        }
    }

    fun setFontSize(newFontSize: Int) {
        _fontSize.value = newFontSize

        viewModelScope.launch {
            settingsManager.saveSettings(
                SettingsData(
                    _language.value,
                    _themeMode.value,
                    newFontSize
                )
            )

        }
    }


    fun getSettings() {
        viewModelScope.launch {
            settingsManager.getSettings.collect { settings ->
                _language.value = settings.language
                _themeMode.value = settings.themeMode
                _fontSize.value = settings.fontSize
                Log.d("SettingsViewModel:init", "Init language: ${_language.value}")
                Log.d("SettingsViewModel:init", "Init themeMode: ${_themeMode.value}")
                Log.d("SettingsViewModel:init", "Init fontSize: ${_fontSize.value}")
            }
        }
    }
}