package net.kep.easy_dc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import net.kep.easy_dc.R
import net.kep.easy_dc.ui.viewmodel.SettingsViewModel

@Composable
fun GuideScreen(
    settingsViewModel: SettingsViewModel
) {
    val themeMode by settingsViewModel.themeMode.collectAsState()
    val isDark = when (themeMode) {
        "Dark" -> true
        "Light" -> false
        else -> isSystemInDarkTheme()
    }
    val pdfState = rememberVerticalPdfReaderState(
        resource =
            if (isDark) ResourceType.Asset(R.raw.theory_ru_dark)
            else ResourceType.Asset(R.raw.theory_ru_light),
        isZoomEnable = true
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}
