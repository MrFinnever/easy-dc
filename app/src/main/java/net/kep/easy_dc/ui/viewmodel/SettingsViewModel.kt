package net.kep.easy_dc.ui.viewmodel

//class SettingsViewModel(private val settingsManager: SettingsManager) : ViewModel() {
//
//    // Получаем настройки как StateFlow для использования в UI
//    val themeMode: StateFlow<String> = settingsManager.themeModeFlow
//        .stateIn(viewModelScope, SharingStarted.Lazily, "system")
//
//    val language: StateFlow<String> = settingsManager.languageFlow
//        .stateIn(viewModelScope, SharingStarted.Lazily, "ru")
//
//    val fontSize: StateFlow<Int> = settingsManager.fontSizeFlow
//        .stateIn(viewModelScope, SharingStarted.Lazily, 3)
//
//
//    // Методы для изменения настроек
//    fun setThemeMode(themeMode: String) {
//        viewModelScope.launch {
//            settingsManager.saveThemeMode(themeMode)
//        }
//    }
//
//    fun setLanguage(language: String) {
//        viewModelScope.launch {
//            settingsManager.saveLanguage(language)
//        }
//    }
//
//    fun setFontSize(fontSize: Int) {
//        viewModelScope.launch {
//            settingsManager.saveFontSize(fontSize)
//        }
//    }
//}



//private val dataStore = context.dataStore
//
//    val fontSizeFlow: Flow<Int> = dataStore.data
//        .map { preferences ->
//            preferences[SettingsKeys.FONT_SIZE] ?: 16 // значение по умолчанию
//        }
//
//
//    val themeModeFlow: Flow<ThemeMode> = dataStore.data
//        .map { preferences ->
//            when (preferences[SettingsKeys.THEME_MODE]) {
//                "DARK" -> ThemeMode.DARK
//                "LIGHT" -> ThemeMode.LIGHT
//                else -> ThemeMode.SYSTEM
//            }
//        }
//
//    suspend fun setThemeMode(context: Context, themeMode: ThemeMode) {
//        context.dataStore.edit { preferences ->
//            preferences[SettingsKeys.THEME_MODE] = themeMode.mode
//        }
//    }
//    suspend fun setFontSize(context: Context, fontSize: Int) {
//        context.dataStore.edit { preferences ->
//            preferences[SettingsKeys.FONT_SIZE] = fontSize
//        }
//    }