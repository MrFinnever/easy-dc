package net.kep.easy_dc.ui.screens

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SensorOccupied
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.launch
import net.kep.easy_dc.R
import net.kep.easy_dc.data.settings.SettingsManager
import net.kep.easy_dc.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    //settingsData: SettingsData,
    settingsManager: SettingsManager
) {
    val settingsViewModel = SettingsViewModel(settingsManager)
    val language by settingsViewModel.language.collectAsState()
    val themeMode = settingsViewModel.themeMode.collectAsState()
    val fontSize = settingsViewModel.fontSize.collectAsState()

    settingsManager.getSettings

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppPresentation()

        Spacer(modifier = Modifier.padding(5.dp))

        Language(
            language = language,
            settingsViewModel = settingsViewModel
        )
//        ThemeSelectionScreen(
//            currentTheme = themeMode.value,
//            onThemeChanged = updateTheme
//        )
//        FontSize(
//            currentFontSize = fontSize.value,
//            onFontSizeChanged = updateFontSize
//        )

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
fun AppPresentation() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
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
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Версия: 0.1.1",
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 0.dp)
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Language(
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
                    imageVector = Icons.Default.Translate,
                    contentDescription = "Language",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.language),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (language == "ru") russianLanguage else englishLanguage,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ListItem(
                    modifier = Modifier.clickable {
                        settingsViewModel.getSettings()
                        settingsViewModel.setSettings("ru")
                        updateLanguage(context, "ru")
                    },
                    headlineContent = { Text(russianLanguage) },
                    trailingContent = {
                        RadioButton(
                            selected = language == "ru",
                            onClick = {
                                settingsViewModel.getSettings()
                                settingsViewModel.setSettings("ru")
                                updateLanguage(context, "ru")
                            }
                        )
                    }
                )


                ListItem(
                    modifier = Modifier.clickable {
                        settingsViewModel.getSettings()
                        settingsViewModel.setSettings("en")
                        updateLanguage(context, "en")
                    },
                    headlineContent = { Text(stringResource(id = R.string.language_english)) },
                    trailingContent = {
                        RadioButton(
                            selected = language != "ru",
                            onClick = {
                                settingsViewModel.getSettings()
                                settingsViewModel.setSettings("en")
                                updateLanguage(context, "en")
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


fun updateLanguage(
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
fun ThemeSelectionScreen(
    currentTheme: String,
    onThemeChanged: (String) -> Unit
) {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val lightThemeMode = stringResource(id = R.string.light_theme_mode)
    val darkThemeMode = stringResource(id = R.string.dark_theme_mode)
    val systemThemeMode = stringResource(id = R.string.system_theme_mode)

    var selectedTheme by remember { mutableStateOf(currentTheme) }

    val displayThemeMode = when (selectedTheme) {
        "Light" -> lightThemeMode
        "Dark" -> darkThemeMode
        "System" -> systemThemeMode
        else -> systemThemeMode
    }

    Card(
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
                    contentDescription = "Theme",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Тема",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = displayThemeMode,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                
                ListItem(
                    modifier = Modifier.clickable {
                        selectedTheme = "Light"
                        onThemeChanged("Light")
                    },
                    headlineContent = { Text("Светлая") },
                    trailingContent = {
                        RadioButton(
                            selected = selectedTheme == "Light",
                            onClick = {
                                selectedTheme = "Light"
                                onThemeChanged("Light")
                            }
                        )
                    }
                )


                ListItem(
                    modifier = Modifier.clickable {
                        selectedTheme = "Dark"
                        onThemeChanged("Dark")
                    },
                    headlineContent = { Text("Тёмная") },
                    trailingContent = {
                        RadioButton(
                            selected = selectedTheme == "Dark",
                            onClick = {
                                selectedTheme = "Light"
                                onThemeChanged("Light")
                            }
                        )
                    }
                )

                ListItem(
                    modifier = Modifier.clickable {
                        selectedTheme = "System"
                        onThemeChanged("System")
                    },
                    headlineContent = { Text("System") },
                    trailingContent = {
                        RadioButton(
                            selected = selectedTheme == "System",
                            onClick = {
                                selectedTheme = "System"
                                onThemeChanged("System")
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authors() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
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
                    imageVector = Icons.Default.Diversity1,
                    contentDescription = "Team",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Команда проекта",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                    sheetState.hide()  // Закрытие листа с анимацией
                    showModalBottomSheet = false
                }
            },
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
                            contentDescription = "Team",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    headlineContent = {
                        Text(
                            text = "Команда проекта"
                        )
                    }
                )

                Divider()

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = "Sound",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    overlineContent = {
                        Text(
                            text = "Куратор проекта"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = "Александра Ларионова"
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
                        }
                    }
                )

                Divider()

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Sound",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    headlineContent = {
                        Text(
                            text = "Иван Лебедев"
                        )
                    },
                    overlineContent = {
                        Text(
                            text = "Программа"
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
                        }
                    }
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Smartphone,
                            contentDescription = "Sound",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    overlineContent = {
                        Text(
                            text = "Приложение"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = "Леонид Крупеньков"
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
                        }
                    }
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = "Sound",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    overlineContent = {
                        Text(
                            text = "Теория"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = "Дмитрий"
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
                        }
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Credits() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
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
                    imageVector = Icons.Default.Handshake,
                    contentDescription = "Team",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Благодарности",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                    sheetState.hide()  // Закрытие листа с анимацией
                    showModalBottomSheet = false
                }
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.SensorOccupied,
                            contentDescription = "Team",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    headlineContent = {
                        Text(
                            text = "Сопровождение"
                        )
                    }
                )

                Divider()

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.QuestionAnswer,
                            contentDescription = "Consulting",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    overlineContent = {
                        Text(
                            text = "Консультация"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = "Денис"
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
                        }
                    }
                )
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.QuestionAnswer,
                            contentDescription = "Consulting",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    overlineContent = {
                        Text(
                            text = "Консультация"
                        )
                    },
                    headlineContent = {
                        Text(
                            text = "Александр Крылов"
                        )
                    },
                    trailingContent = {
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Profile")
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutApp() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
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
                    imageVector = Icons.Default.Work,
                    contentDescription = "About Project",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "О проекте",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                    sheetState.hide()  // Закрытие листа с анимацией
                    showModalBottomSheet = false
                }
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = "*Куча текста про то, как важен этот проект. Может быть даже отдельным окном будет*",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSource() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
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
                    imageVector = Icons.Default.Code,
                    contentDescription = "About Project",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Исходный код",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                    sheetState.hide()  // Закрытие листа с анимацией
                    showModalBottomSheet = false
                }
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = "*Куча текста про то, как важен этот проект. Может быть даже отдельным окном будет*",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun License() {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    Card(
        shape = MaterialTheme.shapes.medium,
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
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Лицензия",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                    sheetState.hide()  // Закрытие листа с анимацией
                    showModalBottomSheet = false
                }
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = "*Куча текста про то, как важен этот проект. Может быть даже отдельным окном будет*",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontSize(
    currentFontSize: Int,
    onFontSizeChanged: (Int) -> Unit
) {
    val context = LocalContext.current

    var showModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var fontSize by remember { mutableFloatStateOf(currentFontSize.toFloat()) }
    var fontSizeName by remember { mutableStateOf(getFontSizeName(fontSize, context)) }



    Card(
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
                    contentDescription = "Font Size",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Размер шрифта",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fontSizeName,
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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


    // Модальный лист для выбора темы
    if (showModalBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalBottomSheet = false
                }
            },
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
                            contentDescription = "Font Size",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    headlineContent = {
                        Text(
                            text = "Размер шрифта: $fontSizeName"
                        )
                    }
                )

                Slider(
                    value = fontSize,
                    steps = 3,
                    valueRange = 1f..5f,
                    onValueChange = {
                        fontSize = it
                        fontSizeName = getFontSizeName(it, context)
                    },
                    onValueChangeFinished = {
                        onFontSizeChanged(fontSize.toInt())
                    },
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



@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val context = LocalContext.current
    SettingsScreen(
        //settingsData = SettingsData(),
        settingsManager = SettingsManager(context)
    )
}