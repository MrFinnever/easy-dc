package net.kep.easy_dc.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import net.kep.easy_dc.data.theme.enums.Corners
import net.kep.easy_dc.data.theme.enums.TextSize


@Composable
fun EasyDCTheme(
    textSize: TextSize = TextSize.Medium,
    corners: Corners = Corners.SuperRounded,
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val textStyles = getTextStyles(textSize)

    val shapesStyles = getShapeStyles(corners)

    //TODO("Paddings")

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalTextStyles provides textStyles,
        LocalShapesStyles provides shapesStyles,
        content = content
    )
}