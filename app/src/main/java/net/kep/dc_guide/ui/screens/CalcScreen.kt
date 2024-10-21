package net.kep.dc_guide.ui.screens

import androidx.collection.MutableIntList
import androidx.collection.mutableIntListOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun CalcScreen(
    calcNavCon: NavController
) {
    val branches = remember { mutableStateListOf(1) }
    val branchIndex = remember { mutableIntListOf(1) }

    Scaffold(
        topBar = {
            CalcTopAppBar(
                calcNavCon = calcNavCon
            )
        },
        floatingActionButton = {
            CalcFAB(
                branchIndex = branchIndex,
                branches = branches
            )
        }
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            branches.forEach { index ->
                BranchCard(
                    branchIndex = branchIndex.last(),
                    branches = branches,
                    Modifier
                        .padding(it)
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                )
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcTopAppBar(
    calcNavCon: NavController
) {
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


@Composable
fun CalcFAB(
    branchIndex: MutableIntList,
    branches: MutableList<Int>
) {
    fun addBranch() {
        branches.add(branches.size)
        branchIndex.add(branchIndex.lastIndex + 1)
    }

    ExtendedFloatingActionButton(
        onClick = { addBranch() },
        icon = {
            Icon(
                imageVector =  Icons.Default.Add,
                contentDescription = "Add a branch"
            )
        },
        text = {
            Text(
                text = "Ветвь"
            )
        }

    )
}


@Composable
fun BranchCard(
    branchIndex: Int,
    branches: SnapshotStateList<Int>,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {

            BranchCardLabel(
                branchIndex = branchIndex,
                branches = branches,
                modifier = Modifier
                    .fillMaxWidth()
            )

            BranchInputOutput(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()

            )

            val resistorsTextFields = remember { mutableStateListOf("") }

            BranchMultiComponent(
                lable = "Резистор",
                placeholder = "Ом",
                textFields = resistorsTextFields,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )

            val emfTextFields = remember { mutableStateListOf("") }

            BranchMultiComponent(
                lable = "ЭДС",
                placeholder = "В",
                textFields = emfTextFields,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun BranchCardLabel(
    branchIndex: Int,
    branches: SnapshotStateList<Int>,
    modifier: Modifier
) {
    fun removeBranch() {
        if (branches.size > 1) {
            branches.removeAt(branchIndex - 1)
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Text(
            text = "Ветвь №$branchIndex",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(

            onClick = {
                removeBranch()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete a branch"
            )
        }
    }
}


@Composable
fun BranchInputOutput(
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        val input = remember{mutableStateOf("")}
        val output = remember{mutableStateOf("")}

        OutlinedTextField(
            label = {
                Text(
                    text = "Вход",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            placeholder = {
                Text(
                    text = "Номер узла",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            shape = MaterialTheme.shapes.medium,
            value = input.value,
            onValueChange = { newText ->
                input.value = newText
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            maxLines = 1,
            modifier = Modifier
                .padding(end = 5.dp)
                .fillMaxWidth(0.49f)
        )
        OutlinedTextField(
            label = {
                Text(
                    text = "Выход",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            placeholder = {
                Text(
                    text = "Номер узла",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            shape = MaterialTheme.shapes.medium,
            value = output.value,
            onValueChange = { newValue ->
                output.value = newValue
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1,
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun BranchMultiComponent(
    lable: String,
    placeholder: String,
    textFields: MutableList<String>,
    modifier: Modifier
) {
    // Функция для добавления нового текстового поля
    fun addTextField() {
        textFields.add("")
    }

    // Функция для удаления последнего текстового поля
    fun removeLastTextField(
        index: Int
    ) {
        if (textFields.size > 1) {
            textFields.removeAt(index)
        }
    }

    Column(
        modifier = modifier
    ) {
        textFields.forEachIndexed { index, text ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = lable,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    placeholder = {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = text,
                    onValueChange = { newText ->
                        textFields[index] = newText
                    },
                    maxLines = 1,
                    modifier = if(index == 0) {
                        Modifier
                            .fillMaxWidth()
                    }
                            else {
                        Modifier
                            .fillMaxWidth(0.85f)
                    }

                )

                if(index > 0) {
                    IconButton(
                        onClick = { removeLastTextField(index) },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(Icons.Default.RemoveCircle, contentDescription = "Delete")
                    }
                }
            }


        }

        OutlinedIconButton(
            shape = MaterialTheme.shapes.medium,
            onClick = {
                addTextField()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

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


@Preview
@Composable
fun CalcScreenPreview() {
    val calcNav = rememberNavController()
    CalcScreen(calcNav)
}