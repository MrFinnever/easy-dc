package net.kep.easy_dc.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import net.kep.easy_dc.data.theme.CustomColors
import net.kep.easy_dc.data.theme.enums.Corners
import net.kep.easy_dc.data.theme.enums.TextSize


internal val LocalColors = staticCompositionLocalOf<CustomColors> {
    DarkColorScheme
}

internal val LocalTextStyles = staticCompositionLocalOf {
    getTextStyles(TextSize.Medium)
}

internal val LocalShapesStyles = staticCompositionLocalOf {
    getShapeStyles(Corners.SuperRounded)
}



object LuxuryTheme {
    internal val colors
        @Composable get() = LocalColors.current
}