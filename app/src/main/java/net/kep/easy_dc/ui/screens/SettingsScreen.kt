package net.kep.easy_dc.ui.screens

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.launch
import net.kep.easy_dc.R
import net.kep.easy_dc.data.settings.SettingsManager
import net.kep.easy_dc.ui.theme.LocalColors
import net.kep.easy_dc.ui.theme.LocalTextStyles
import net.kep.easy_dc.ui.viewmodel.SettingsViewModel
import java.util.Locale

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    settingsManager: SettingsManager
) {

    val language by settingsViewModel.language.collectAsState()
    val themeMode by settingsViewModel.themeMode.collectAsState()
    val fontSize = settingsViewModel.fontSize.collectAsState()

    settingsManager.getSettings

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.background)
            .verticalScroll(rememberScrollState())
    ) {
        AppPresentation()

        Spacer(modifier = Modifier.padding(5.dp))

        Language(
            language = language,
            settingsViewModel = settingsViewModel
        )

        ThemeMode(
            currentTheme = themeMode,
            settingsViewModel = settingsViewModel
        )

        FontSize(
            currentFontSize = fontSize.value,
            settingsViewModel = settingsViewModel
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Authors()
        Credits()

        Spacer(modifier = Modifier.padding(5.dp))

        AboutApp()
        OpenSource()
        License()
    }

}


@Composable
private fun AppPresentation() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
            .background(LocalColors.current.background)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(40.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Icon",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = LocalTextStyles.current.title.fontSize,
            color = LocalColors.current.onBackground,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.version) + " "
                    + stringResource(id = R.string.version_number),
            fontSize = LocalTextStyles.current.subText.fontSize,
            color = LocalColors.current.onBackground,
            modifier = Modifier.padding(top = 0.dp)
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Language(
    language: String,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current

    val russianLanguage = stringResource(id = R.string.language_russian)
    val englishLanguage = stringResource(id = R.string.language_english)


    Log.d("SettingsScreen:Language", "language: $language")



    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val coroutineScope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { showModalBottomSheet = true }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Translate,
                    contentDescription = "Language",
                    tint = LocalColors.current.onSurface
                )
                Text(
                    text = stringResource(id = R.string.language),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (language == "ru") russianLanguage else englishLanguage,
                    color = LocalColors.current.settingsTextIndicator,
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Choose",
                    modifier = Modifier.size(20.dp)
                )
            }

        }
    }

    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ListItem(
                    modifier = Modifier.clickable {
                        settingsViewModel.getSettings()
                        settingsViewModel.setLanguage("ru")
                        updateLanguage(context, "ru")
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.language_russian),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        )
                    },
                    trailingContent = {
                        RadioButton(
                            selected = language == "ru",
                            onClick = {
                                settingsViewModel.getSettings()
                                settingsViewModel.setLanguage("ru")
                                updateLanguage(context, "ru")
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalColors.current.primary,
                                unselectedColor = LocalColors.current.onSurface
                            )
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.primary
                    )
                )


                ListItem(
                    modifier = Modifier.clickable {
                        settingsViewModel.getSettings()
                        settingsViewModel.setLanguage("en")
                        updateLanguage(context, "en")
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.language_english),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        ) },
                    trailingContent = {
                        RadioButton(
                            selected = language != "ru",
                            onClick = {
                                settingsViewModel.getSettings()
                                settingsViewModel.setLanguage("en")
                                updateLanguage(context, "en")
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalColors.current.primary,
                                unselectedColor = LocalColors.current.onSurface
                            )
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.primary
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


private fun updateLanguage(
    context: Context,
    newLanguage: String
) {
    restartActivity(context, newLanguage)
}

private fun restartActivity(
    context: Context,
    newLanguage: String
) {
    context.findActivity()?.runOnUiThread {
        val appLocale = when (newLanguage) {
            "ru" -> LocaleListCompat.forLanguageTags("ru")
            "en" -> LocaleListCompat.forLanguageTags("en")
            else -> LocaleListCompat.forLanguageTags("en")
        }
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeMode(
    currentTheme: String,
    settingsViewModel: SettingsViewModel
) {
    Log.d("SettingsScreen:ThemeMode", "currentTheme: $currentTheme")

    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val lightThemeMode = stringResource(id = R.string.light_theme_mode)
    val darkThemeMode = stringResource(id = R.string.dark_theme_mode)
    val systemThemeMode = stringResource(id = R.string.system_theme_mode)

    val displayThemeMode = when (currentTheme) {
        "Light" -> lightThemeMode
        "Dark" -> darkThemeMode
        "System" -> systemThemeMode
        else -> systemThemeMode
    }
    Log.d("SettingsScreen:ThemeMode", "displayThemeMode: $displayThemeMode")

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { showModalBottomSheet = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FormatPaint,
                    contentDescription = "Theme"
                )
                Text(
                    text = stringResource(id = R.string.theme),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = displayThemeMode,
                    color = LocalColors.current.settingsTextIndicator,
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Choose",
                    modifier = Modifier.size(20.dp)
                )
            }

        }
    }

    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface,

            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                
                ListItem(
                    modifier = Modifier.clickable {
                        settingsViewModel.setThemeMode("Light")
                        Log.d("SettingsScreen", "Pressed Set Light Theme")
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.light_theme_mode),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        ) },
                    trailingContent = {
                        RadioButton(
                            selected = currentTheme == "Light",
                            onClick = {
                                settingsViewModel.setThemeMode("Light")
                                Log.d("SettingsScreen", "Pressed Set Light Theme")
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalColors.current.primary,
                                unselectedColor = LocalColors.current.onSurface
                            )
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.primary
                    )
                )


                ListItem(
                    modifier = Modifier.clickable {
                        settingsViewModel.setThemeMode("Dark")
                        Log.d("SettingsScreen", "Pressed Set Dark Theme")
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.dark_theme_mode),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        ) },
                    trailingContent = {
                        RadioButton(
                            selected = currentTheme == "Dark",
                            onClick = {
                                settingsViewModel.setThemeMode("Dark")
                                Log.d("SettingsScreen", "Pressed Set Dark Theme")
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalColors.current.primary,
                                unselectedColor = LocalColors.current.onSurface
                            )
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.primary
                    )
                )

                ListItem(
                    modifier = Modifier.clickable {
                        settingsViewModel.setThemeMode("System")
                        Log.d("SettingsScreen", "Pressed Set System Theme")
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.system_theme_mode),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        ) },
                    trailingContent = {
                        RadioButton(
                            selected = currentTheme == "System",
                            onClick = {
                                settingsViewModel.setThemeMode("System")
                                Log.d("SettingsScreen", "Pressed Set Dark Theme")
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalColors.current.primary,
                                unselectedColor = LocalColors.current.onSurface
                            )
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.primary
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FontSize(
    currentFontSize: Int,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current

    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var fontSize by remember { mutableFloatStateOf(currentFontSize.toFloat()) }
    var fontSizeName by remember { mutableStateOf(getFontSizeName(fontSize, context)) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { showModalBottomSheet = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FontDownload,
                    contentDescription = "Font Size"
                )
                Text(
                    text = stringResource(id = R.string.font_size),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fontSizeName,
                    color = LocalColors.current.settingsTextIndicator,
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Choose",
                    modifier = Modifier.size(20.dp)
                )
            }

        }
    }

    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.FontDownload,
                            contentDescription = "Font Size"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.font_size) + ": $fontSizeName",
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        leadingIconColor = LocalColors.current.onSurface
                    )
                )

                Slider(
                    value = fontSize,
                    steps = 3,
                    valueRange = 1f..5f,
                    onValueChange = {
                        fontSize = it
                        fontSizeName = getFontSizeName(it, context)
                        settingsViewModel.setFontSize(it.toInt())
                    },
                    onValueChangeFinished = {
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = LocalColors.current.primary,
                        activeTrackColor = LocalColors.current.primary,
                        disabledInactiveTickColor = LocalColors.current.primary
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )



                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


private fun getFontSizeName(fontSize: Float, context: Context): String {
    return when (fontSize) {
        1f -> context.getString(R.string.font_size_very_small)
        2f -> context.getString(R.string.font_size_small)
        3f -> context.getString(R.string.font_size_medium)
        4f -> context.getString(R.string.font_size_large)
        5f -> context.getString(R.string.font_size_very_large)
        else -> context.getString(R.string.font_size_medium)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Authors() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { showModalBottomSheet = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Diversity1,
                    contentDescription = "Team",
                    tint = LocalColors.current.onSurface
                )
                Text(
                    text = stringResource(id = R.string.project_team),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Choose",
                modifier = Modifier.size(20.dp)
            )
        }
    }

    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Diversity1,
                            contentDescription = "Team"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.project_team),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )

                )

                Divider(
                    color = LocalColors.current.divider
                )

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = "Sound"
                        )
                    },
                    overlineContent = {
                        Text(
                            text = stringResource(id = R.string.project_supervisor),
                            fontSize = LocalTextStyles.current.settingsSubItem.fontSize
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.Alexandra_Larionova),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        )
                    },
//                    trailingContent = {
//                        IconButton(
//                            onClick = { /*TODO*/ }
//                        ) {
//                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
//                        }
//                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )
                )

                Divider(
                    color = LocalColors.current.divider
                )

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Sound"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.Ivan_Lebedev),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        )
                    },
                    overlineContent = {
                        Text(
                            text = stringResource(id = R.string.program),
                            fontSize = LocalTextStyles.current.settingsSubItem.fontSize
                        )
                    },
//                    trailingContent = {
//                        IconButton(
//                            onClick = { /*TODO*/ }
//                        ) {
//                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
//                        }
//                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Smartphone,
                            contentDescription = "Sound"
                        )
                    },
                    overlineContent = {
                        Text(
                            text = stringResource(id = R.string.application),
                            fontSize = LocalTextStyles.current.settingsSubItem.fontSize
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.Leonid_Krupenkov),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        )
                    },
//                    trailingContent = {
//                        IconButton(
//                            onClick = { /*TODO*/ }
//                        ) {
//                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
//                        }
//                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = "Sound"
                        )
                    },
                    overlineContent = {
                        Text(
                            text = stringResource(id = R.string.guide),
                            fontSize = LocalTextStyles.current.settingsSubItem.fontSize
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.Dmitry),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize
                        )
                    },
//                    trailingContent = {
//                        IconButton(
//                            onClick = { /*TODO*/ }
//                        ) {
//                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
//                        }
//                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )
                )
                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Credits() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { showModalBottomSheet = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Handshake,
                    contentDescription = "Team",
                    tint = LocalColors.current.onSurface
                )
                Text(
                    text = stringResource(id = R.string.credits),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Choose",
                modifier = Modifier.size(20.dp)
            )
        }
    }


    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Handshake,
                            contentDescription = "Team"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.credits),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize,
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )
                )

                Divider(
                    color = LocalColors.current.divider
                )

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.QuestionAnswer,
                            contentDescription = "Consulting"
                        )
                    },
                    overlineContent = {
                        Text(
                            text = stringResource(id = R.string.consultation),
                            fontSize = LocalTextStyles.current.settingsSubItem.fontSize,
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.Denis),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize,
                        )
                    },
//                    trailingContent = {
//                        IconButton(
//                            onClick = { /*TODO*/ }
//                        ) {
//                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
//                        }
//                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.QuestionAnswer,
                            contentDescription = "Consulting"
                        )
                    },
                    overlineContent = {
                        Text(
                            text = stringResource(id = R.string.consultation),
                            fontSize = LocalTextStyles.current.settingsSubItem.fontSize,
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(id = R.string.Alexander_Krylov),
                            fontSize = LocalTextStyles.current.settingsItem.fontSize,
                        )
                    },
//                    trailingContent = {
//                        IconButton(
//                            onClick = { /*TODO*/ }
//                        ) {
//                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
//                        }
//                    },
                    colors = ListItemDefaults.colors(
                        containerColor = LocalColors.current.surface,
                        headlineColor = LocalColors.current.onSurface,
                        overlineColor = LocalColors.current.overlineText,

                        leadingIconColor = LocalColors.current.onSurface,
                        trailingIconColor = LocalColors.current.onSurface
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutApp() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { showModalBottomSheet = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "About Project",
                    tint = LocalColors.current.onSurface
                )
                Text(
                    text = stringResource(id = R.string.about_project),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Choose",
                modifier = Modifier.size(20.dp)
            )
        }
    }

    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = localizedText(),
                    fontSize = LocalTextStyles.current.standard.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


@Composable
private fun localizedText(): String {
    val fileName = when (Locale.getDefault().language) {
        "ru" -> "about_project_ru.txt"
        "en" -> "about_project_en.txt"
        else -> "about_project_en.txt"
    }

    val context = LocalContext.current
    val inputStream = context.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OpenSource() {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/MrFinnever/easy-dc")
                )
                context.startActivity(intent)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = "About Project",
                    tint = LocalColors.current.onSurface
                )
                Text(
                    text = stringResource(id = R.string.open_source),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Choose",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun License() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 10.dp, bottom = 20.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { showModalBottomSheet = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Policy,
                    contentDescription = "About Project",
                    tint = LocalColors.current.onSurface
                )
                Text(
                    text = stringResource(id = R.string.license),
                    fontSize = LocalTextStyles.current.settingsItem.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Choose",
                modifier = Modifier.size(20.dp)
            )
        }
    }

    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
            containerColor = LocalColors.current.surface,
            contentColor = LocalColors.current.onSurface,
            sheetState = sheetState
        ) {
            LicenseDescription()
        }
    }
}


@Composable
fun LicenseDescription() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.app_under_license),
            fontSize = LocalTextStyles.current.standard.fontSize,
            fontStyle = MaterialTheme.typography.titleMedium.fontStyle
        )
        Text(
            text = stringResource(id = R.string.license_rights),
            fontSize = LocalTextStyles.current.title.fontSize,
            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        )


        RightsCard(
            right = stringResource(id = R.string.license_right_use),
            icon = Icons.Default.AutoAwesome
        )
        RightsCard(
            right = stringResource(id = R.string.license_right_modify),
            icon = Icons.Default.AutoFixHigh
        )
        RightsCard(
            right = stringResource(id = R.string.license_right_share),
            icon = Icons.Default.Diversity3
        )



        Spacer(modifier = Modifier.padding(vertical = 50.dp))
    }
}

@Composable
fun RightsCard(
    right: String,
    icon: ImageVector
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.primary,
            contentColor = LocalColors.current.onPrimary
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth()

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = right
                )
                Text(
                    text = right,
                    fontSize = LocalTextStyles.current.bigButtonsText.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}



@Preview(showBackground = true, locale = "en")
@Composable
fun SettingsScreenPreview() {
    val context = LocalContext.current
    LicenseDescription()
//    SettingsScreen(
//        //settingsData = SettingsData(),
//        settingsManager = SettingsManager(context)
//    )
}
@Preview(showBackground = true, locale = "ru")
@Composable
fun SettingsScreenPreviewRu() {
    val context = LocalContext.current
    LicenseDescription()
//    SettingsScreen(
//        //settingsData = SettingsData(),
//        settingsManager = SettingsManager(context)
//    )
}