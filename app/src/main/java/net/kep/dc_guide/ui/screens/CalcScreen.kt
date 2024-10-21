package net.kep.dc_guide.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType


@ExperimentalMaterial3Api
@Composable
fun CalcScreen(
    calcNavCon: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Расчёт цепи",
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    IconButton(
                        onClick = { TODO("НАВИГАЦИЯ НА ЭКРАН С СОВЕТОМ") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.QuestionMark,
                            contentDescription = "Back"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { calcNavCon.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) {
        Calculator(
            Modifier.padding(it)
        )
    }
}


@Composable
fun Calculator(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = "Введите ветви:",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(branches) { branch ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextFieldWithLabel(
                        value = branch.input.toString(),
                        label = "Int",
                        onValueChange = { },
                        keyboardType = KeyboardType.Number
                    )
                    TextFieldWithLabel(
                        value = branch.emf.toString(),
                        label = "Double 1",
                        onValueChange = { },
                        keyboardType = KeyboardType.Decimal
                    )
                    TextFieldWithLabel(
                        value = "",
                        label = "Double 2",
                        onValueChange = { },
                        keyboardType = KeyboardType.Decimal
                    )
                    TextFieldWithLabel(
                        value = branch.input.toString(),
                        label = "Int",
                        onValueChange = { },
                        keyboardType = KeyboardType.Number
                    )
                }
                Button(
                        onClick = { TODO() },
                modifier = Modifier
                    .padding(top = 16.dp)
                ) {
                Text("Добавить ветвь")
                }
            }

        }



    }
}


@Composable
fun TextFieldWithLabel(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp) // ширина каждого TextField
            .padding(horizontal = 8.dp) // добавляем отступы для визуального разделения
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


data class Branch(
    val input: Int,
    val emf: Double,
    val resist: Double,
    val output: Int
)
val branches: List<Branch> = listOf(
    Branch(1, 2.0, 3.0, 2),
    Branch(2, 2.5, 3.1, 3),
    Branch(3, 1.0, 3.3, 4),
    Branch(4, 3.0, 1.4, 1)
)


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CalcScreenPreview() {
    val calcNav = rememberNavController()
    CalcScreen(calcNav)
}