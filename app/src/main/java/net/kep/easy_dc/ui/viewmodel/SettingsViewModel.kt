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

    private val _language = MutableStateFlow("null")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _themeMode = MutableStateFlow("System")
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    private val _fontSize = MutableStateFlow(3)
    val fontSize: StateFlow<Int> = _fontSize.asStateFlow()

    init {
        viewModelScope.launch {
            settingsManager.getSettings.collect { settings ->
                if (_language.value == "null")
                    _language.value = settings.language
            }
        }
        Log.d("VM", "Language: ${_language.value}")
    }

    fun setSettings(newLanguage: String) {
        viewModelScope.launch {
            settingsManager.saveSettings(
                SettingsData(
                    newLanguage
                )
            )
            _language.value = newLanguage
        }
    }

    fun getSettings() {
        viewModelScope.launch {
            settingsManager.getSettings.collect { settings ->
                _language.value = settings.language
                _themeMode.value = settings.themeMode
                _fontSize.value = settings.fontSize
            }
        }
    }
}