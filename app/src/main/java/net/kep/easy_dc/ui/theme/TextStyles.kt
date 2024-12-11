package net.kep.easy_dc.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import net.kep.easy_dc.data.theme.CustomTextStyle
import net.kep.easy_dc.data.theme.enums.TextSize

internal fun getTextStyles(textSize: TextSize): CustomTextStyle {
    return CustomTextStyle(
        bigTitle = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 32.sp
                TextSize.Small -> 40.sp
                TextSize.Medium -> 48.sp
                TextSize.Big -> 56.sp
                TextSize.Large -> 64.sp
            },
            fontWeight = FontWeight.SemiBold
        ),

        title = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 20.sp
                TextSize.Small -> 23.sp
                TextSize.Medium -> 26.sp
                TextSize.Big -> 29.sp
                TextSize.Large -> 32.sp
            },
            fontWeight = FontWeight.SemiBold
        ),

        bigButtonsText = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 15.sp
                TextSize.Small -> 20.sp
                TextSize.Medium -> 25.sp
                TextSize.Big -> 30.sp
                TextSize.Large -> 35.sp
            }
        ),

        navigationText = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 8.sp
                TextSize.Small -> 10.sp
                TextSize.Medium -> 12.sp
                TextSize.Big -> 14.sp
                TextSize.Large -> 16.sp
            }
        ),

        subText = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 10.sp
                TextSize.Small -> 12.sp
                TextSize.Medium -> 14.sp
                TextSize.Big -> 16.sp
                TextSize.Large -> 18.sp
            }
        ),

        settingsItem = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 10.sp
                TextSize.Small -> 13.sp
                TextSize.Medium -> 16.sp
                TextSize.Big -> 19.sp
                TextSize.Large -> 22.sp
            }
        ),

        settingsSubItem = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 6.sp
                TextSize.Small -> 8.sp
                TextSize.Medium -> 10.sp
                TextSize.Big -> 12.sp
                TextSize.Large -> 14.sp
            }
        ),

        standard = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 10.sp
                TextSize.Small -> 13.sp
                TextSize.Medium -> 16.sp
                TextSize.Big -> 19.sp
                TextSize.Large -> 22.sp
            }
        ),

        cardTitle = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 16.sp
                TextSize.Small -> 19.sp
                TextSize.Medium -> 22.sp
                TextSize.Big -> 25.sp
                TextSize.Large -> 28.sp
            }
        ),

        cardText = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 12.sp
                TextSize.Small -> 15.sp
                TextSize.Medium -> 18.sp
                TextSize.Big -> 21.sp
                TextSize.Large -> 24.sp
            }
        ),

        navigationTitle = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 16.sp
                TextSize.Small -> 19.sp
                TextSize.Medium -> 22.sp
                TextSize.Big -> 25.sp
                TextSize.Large -> 28.sp
            }
        ),

        stepTitle = TextStyle(
            fontSize = when (textSize) {
                TextSize.Tiny -> 16.sp
                TextSize.Small -> 19.sp
                TextSize.Medium -> 22.sp
                TextSize.Big -> 25.sp
                TextSize.Large -> 28.sp
            }
        )
    )
}