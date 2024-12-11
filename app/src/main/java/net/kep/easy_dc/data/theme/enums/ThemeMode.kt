package net.kep.easy_dc.data.theme.enums

enum class ThemeMode(val mode: String) {
    LIGHT("LIGHT"),
    DARK("DARK"),
    SYSTEM("SYSTEM");

    companion object {
        fun fromString(value: String): ThemeMode {
            return entries.find { it.mode == value } ?: SYSTEM
        }
    }
}