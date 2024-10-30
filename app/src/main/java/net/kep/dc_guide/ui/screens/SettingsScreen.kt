package net.kep.dc_guide.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SensorOccupied
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var isDark by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Настройки В РАЗРАБОТКЕ",
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                }
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
                .padding(it)
        ) {
            Column {
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.DarkMode,
                            contentDescription = "Dark Mode",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    headlineContent = {
                        Text(
                            text = "Тёмная тема"
                        )
                    },
                    trailingContent = {
                        Switch(checked = isDark, onCheckedChange = { isDark = it })
                    }
                )

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.FontDownload,
                            contentDescription = "Font size",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    headlineContent = {
                        Text(
                            text = "Размер шрифта"
                        )
                    },
                    trailingContent = {
                        Switch(checked = isDark, onCheckedChange = { isDark == it })
                    }
                )

                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Sound",
                            tint = MaterialTheme.colorScheme.onSurface)
                    },
                    headlineContent = {
                        Text(
                            text = "Фоновая музыка"
                        )
                    },
                    trailingContent = {
                        Switch(checked = isDark, onCheckedChange = { isDark = it })
                    }
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp))
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

                Divider()

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
                            tint = MaterialTheme.colorScheme.onSurface)
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
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}