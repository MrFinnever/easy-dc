package net.kep.dc_guide.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneOutline
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
    val branches = remember { mutableStateListOf(Branch(1)) }

    Scaffold(
        topBar = {
            CalcTopAppBar(
                calcNavCon = calcNavCon
            )
        },
        floatingActionButton = {
            ResultButton(
                branches = branches
            )
        }
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                //Для удобного доступа в самом низу экрана
                .padding(bottom = 80.dp)
        ) {
            branches.forEachIndexed { index, branch ->
                BranchCard(
                    branch = branch,
                    isRemovable = index > 0,
                    onRemove = {
                        if (index > 0) {
                            branches.removeAt(index)
                            // Переиндексация оставшихся ветвей
                            branches.forEachIndexed { i, _ ->
                                branches[i].id = i + 1
                            }
                        }
                    },
                    modifier = if (index > 0) {
                        Modifier
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                    }
                    else {
                        Modifier
                            .padding(it)
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                    }

                )
            }

            AddBranchButton(
                branches = branches,
                modifier = Modifier.fillMaxWidth()
            )

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
                onClick = { calcNavCon.navigate(route = "advice") }
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
fun ResultButton(
    branches: SnapshotStateList<Branch>
) {
    ExtendedFloatingActionButton(onClick = { /*TODO*/ }) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Make result"
            )
            Text(
                text = "Рассчитать",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}


@Composable
fun AddBranchButton(
    branches: SnapshotStateList<Branch>,
    modifier: Modifier
) {
    fun addBranch() {
        branches.add(
            Branch(branches.size + 1)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Button(
            onClick = { addBranch() },
            shape = MaterialTheme.shapes.medium,
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавить ветвь"
                    )
                    Text(
                        text = "Добавить ветвь",
                        fontSize = 18.sp
                    )
                }
            }
        )
    }
}


@Composable
fun BranchCard(
    branch: Branch,
    isRemovable: Boolean,
    onRemove: () -> Unit,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
        ) {

            BranchCardLabel(
                branchNumber = branch.id,
                isRemovable = isRemovable,
                onRemove = onRemove,
                modifier = Modifier.fillMaxWidth()
            )

            BranchInputOutput(
                input = branch.input,
                output = branch.output,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )

            BranchMultiComponent(
                label = "Резистор",
                placeholder = "Ом",
                textFields = branch.resistors,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )
            BranchMultiComponent(
                label = "ЭДС",
                placeholder = "В",
                textFields = branch.emf,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun BranchCardLabel(
    branchNumber: Int,
    isRemovable: Boolean,
    onRemove: () -> Unit,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Text(
            text = "Ветвь №$branchNumber",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (isRemovable) {
            IconButton(
                onClick = onRemove
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить ветвь"
                )
            }
        }
    }
}


@Composable
fun BranchInputOutput(
    input: MutableState<String>,
    output: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            value = input.value,  // Используем значение из состояния
            onValueChange = { newText ->
                input.value = newText  // Обновляем состояние
            },
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            value = output.value,
            onValueChange = { newValue ->
                output.value = newValue
            },
            maxLines = 1,
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun BranchMultiComponent(
    label: String,
    placeholder: String,
    textFields: SnapshotStateList<String>,
    modifier: Modifier = Modifier
) {
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
                            text = label,
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
                    modifier = if (index == 0) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier.fillMaxWidth(0.85f)
                    }
                )

                if (index > 0) {
                    IconButton(
                        onClick = {
                            if (textFields.size > 1) textFields.removeAt(index)
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(Icons.Default.RemoveCircle, contentDescription = "Delete")
                    }
                }
            }
        }

        OutlinedIconButton(
            shape = MaterialTheme.shapes.medium,
            onClick = { textFields.add("") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}




data class Branch(
    var id: Int,
    var input: MutableState<String> = mutableStateOf(""),
    var output: MutableState<String> = mutableStateOf(""),
    var resistors: SnapshotStateList<String> = mutableStateListOf(""),
    var emf: SnapshotStateList<String> = mutableStateListOf("")
)



@Preview
@Composable
fun CalcScreenPreview() {
    val calcNav = rememberNavController()
    CalcScreen(calcNav)
}