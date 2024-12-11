package net.kep.easy_dc.ui.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.kep.easy_dc.R
import net.kep.easy_dc.data.calculator.BranchUI
import net.kep.easy_dc.data.calculator.ErrorsInBranch
import net.kep.easy_dc.ui.theme.LocalColors
import net.kep.easy_dc.ui.theme.LocalTextStyles
import net.kep.easy_dc.ui.viewmodel.CalculatorViewModel


@Composable
fun CalcScreen(
    calculatorViewModel: CalculatorViewModel = viewModel(),
    calcNavCon: NavController
) {
    val branches by calculatorViewModel.branches.collectAsState()
    val errorsInBranch by calculatorViewModel.errorsInBranches.collectAsState()
    val showErrorAlert by calculatorViewModel.showErrorAlert.collectAsState()

    Scaffold(
        topBar = {
            CalcTopAppBar(
                calcNavCon = calcNavCon
            )
        },
        floatingActionButton = {
            ResultButton(
                calcNavCon = calcNavCon,
                calculatorViewModel = calculatorViewModel
            )
        },
        containerColor = LocalColors.current.background
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            branches.forEachIndexed { index, branch ->
                val error = errorsInBranch.getOrNull(index) ?: ErrorsInBranch()

                BranchCard(
                    branchUI = branch,
                    isRemovable = index > 0,
                    onRemove = {
                        if (index > 0) {
                            calculatorViewModel.removeBranch(index)
                        }
                    },
                    errorsInBranch = error,
                    modifier = if (index > 0) {
                        Modifier
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                    } else {
                        Modifier
                            .padding(it)
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                    }
                )
            }

            AddBranchButton(
                vm = calculatorViewModel,
                modifier = Modifier.fillMaxWidth()
            )

            if (showErrorAlert) {
                ErrorAlert(
                    calculatorViewModel = calculatorViewModel,
                    errorsInBranchList = errorsInBranch
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
                text = stringResource(id = R.string.calculate_circuit),
                fontSize = LocalTextStyles.current.navigationTitle.fontSize,
            )
        },
        actions = {
//            IconButton(
//                onClick = { calcNavCon.navigate(route = "help") }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.QuestionMark,
//                    contentDescription = "Help"
//                )
//            }
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
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalColors.current.surface,
            navigationIconContentColor = LocalColors.current.onSurface,
            titleContentColor = LocalColors.current.onSurface,
            actionIconContentColor = LocalColors.current.onSurface
        )
    )
}



@Composable
fun ResultButton(
    calcNavCon: NavController,
    calculatorViewModel: CalculatorViewModel
) {
    ExtendedFloatingActionButton(
        onClick = {
            calculatorViewModel.makeResult(calcNavCon = calcNavCon)
        },
        containerColor = LocalColors.current.onSurface,
        contentColor = LocalColors.current.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Make result"
            )
            Text(
                text = stringResource(id = R.string.calculate),
                fontSize = LocalTextStyles.current.cardTitle.fontSize,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }



}


@Composable
fun AddBranchButton(
    vm: CalculatorViewModel,
    modifier: Modifier
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Button(
            onClick = { vm.addBranch() },
            shape = MaterialTheme.shapes.medium,
//            elevation = ButtonDefaults.buttonElevation(
//                defaultElevation = 2.dp
//            ),
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Branch"
                    )
                    Text(
                        text = stringResource(id = R.string.add_branch),
                        fontSize = LocalTextStyles.current.standard.fontSize
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LocalColors.current.surface,
                contentColor = LocalColors.current.onSurface
            )
        )
    }
}


@Composable
fun BranchCard(
    branchUI: BranchUI,
    isRemovable: Boolean,
    onRemove: () -> Unit,
    errorsInBranch: ErrorsInBranch,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        ) {

            BranchCardLabel(
                branchNumber = branchUI.id,
                isRemovable = isRemovable,
                onRemove = onRemove,
                modifier = Modifier.fillMaxWidth()
            )

            BranchInputOutput(
                input = branchUI.input,
                output = branchUI.output,
                errorsInBranch = errorsInBranch,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )

            BranchMultiComponent(
                label = stringResource(id = R.string.emf),
                placeholder = stringResource(id = R.string.volt),
                textFields = branchUI.emf,
                fieldErrors = errorsInBranch.isEMFError,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            )

            BranchMultiComponent(
                label = stringResource(id = R.string.resistor),
                placeholder = stringResource(id = R.string.ohm),
                textFields = branchUI.resistors,
                fieldErrors = errorsInBranch.isResistorsError,
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
            text = stringResource(id = R.string.branch) + " $branchNumber",
            fontSize = LocalTextStyles.current.cardTitle.fontSize,
            color = LocalColors.current.onSurface
        )

        if (isRemovable) {
            IconButton(
                onClick = onRemove
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Branch",
                    tint = LocalColors.current.elementOnSurface
                )
            }
        }
    }
}


@Composable
fun BranchInputOutput(
    input: MutableState<String>,
    output: MutableState<String>,
    errorsInBranch: ErrorsInBranch,
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
                    text = stringResource(id = R.string.input),
                    fontSize = LocalTextStyles.current.standard.fontSize,
                    color = LocalColors.current.onSurface
                )
            },
            textStyle = LocalTextStyles.current.standard,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.node_number),
                    fontSize = LocalTextStyles.current.standard.fontSize
                )
            },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            value = input.value,
            onValueChange = { newText ->
                input.value = newText
                
            },
            isError = errorsInBranch.isInputError.value,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedSupportingTextColor = LocalColors.current.onSurface,
                focusedPlaceholderColor = LocalColors.current.onSurface,
                unfocusedPlaceholderColor = LocalColors.current.onSurface,
                unfocusedTextColor = LocalColors.current.onSurface,
                focusedTextColor = LocalColors.current.onSurface,
                unfocusedLabelColor = LocalColors.current.onSurface,
                focusedLabelColor = LocalColors.current.onSurface,
                cursorColor = LocalColors.current.onSurface,
                focusedIndicatorColor = LocalColors.current.primary,
                focusedContainerColor = LocalColors.current.surface,
                unfocusedIndicatorColor = LocalColors.current.onSurface.copy(0.3f),
                unfocusedContainerColor = LocalColors.current.surface
            ),
            modifier = Modifier
                .padding(end = 5.dp)
                .fillMaxWidth(0.49f)
        )

        OutlinedTextField(
            label = {
                Text(
                    text = stringResource(id = R.string.output),
                    fontSize = LocalTextStyles.current.standard.fontSize,
                    color = LocalColors.current.onSurface
                )
            },
            textStyle = LocalTextStyles.current.standard,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.node_number),
                    fontSize = LocalTextStyles.current.standard.fontSize
                )
            },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            value = output.value,
            onValueChange = { newText ->
                output.value = newText
            },
            colors = TextFieldDefaults.colors(
                focusedSupportingTextColor = LocalColors.current.onSurface,
                focusedPlaceholderColor = LocalColors.current.onSurface,
                unfocusedPlaceholderColor = LocalColors.current.onSurface,
                unfocusedTextColor = LocalColors.current.onSurface,
                focusedTextColor = LocalColors.current.onSurface,
                unfocusedLabelColor = LocalColors.current.onSurface,
                focusedLabelColor = LocalColors.current.onSurface,
                cursorColor = LocalColors.current.onSurface,
                focusedIndicatorColor = LocalColors.current.primary,
                focusedContainerColor = LocalColors.current.surface,
                unfocusedIndicatorColor = LocalColors.current.onSurface.copy(0.3f),
                unfocusedContainerColor = LocalColors.current.surface
            ),
            isError = errorsInBranch.isOutputError.value,
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
    fieldErrors: SnapshotStateList<Boolean>,
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
                val isError = if (index < fieldErrors.size) fieldErrors[index] else false
                OutlinedTextField(
                    label = {
                        Text(
                            text = label,
                            fontSize = LocalTextStyles.current.standard.fontSize,
                            color = LocalColors.current.onSurface
                        )
                    },
                    textStyle = LocalTextStyles.current.standard,
                    placeholder = {
                        Text(
                            text = placeholder,
                            fontSize = LocalTextStyles.current.standard.fontSize,
                            color = LocalColors.current.onSurface
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
                    colors = TextFieldDefaults.colors(
                        focusedSupportingTextColor = LocalColors.current.onSurface,
                        focusedPlaceholderColor = LocalColors.current.onSurface,
                        unfocusedPlaceholderColor = LocalColors.current.onSurface,
                        unfocusedTextColor = LocalColors.current.onSurface,
                        focusedTextColor = LocalColors.current.onSurface,
                        unfocusedLabelColor = LocalColors.current.onSurface,
                        focusedLabelColor = LocalColors.current.onSurface,
                        cursorColor = LocalColors.current.onSurface,
                        focusedIndicatorColor = LocalColors.current.primary,
                        focusedContainerColor = LocalColors.current.surface,
                        unfocusedIndicatorColor = LocalColors.current.onSurface.copy(0.3f),
                        unfocusedContainerColor = LocalColors.current.surface
                    ),
                    isError = isError,
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
                            if (textFields.size > 1) {
                                textFields.removeAt(index)
                            }
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.RemoveCircle,
                            contentDescription = "Delete parameter",
                            tint = LocalColors.current.elementOnSurface
                        )
                    }
                }
            }
        }

        OutlinedIconButton(
            shape = MaterialTheme.shapes.medium,
            onClick = { textFields.add("") },
            colors =  IconButtonDefaults.iconButtonColors(
                contentColor = LocalColors.current.onSurface
            ),
            border = BorderStroke(1.dp, LocalColors.current.onSurface.copy(0.3f)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add parameter")
        }
    }
}


@Composable
fun ErrorAlert(
    calculatorViewModel: CalculatorViewModel,
    errorsInBranchList: List<ErrorsInBranch>
) {
    AlertDialog(
        onDismissRequest = { calculatorViewModel.hideErrorAlert() },
        containerColor = LocalColors.current.surface,
        titleContentColor = LocalColors.current.onSurface,
        textContentColor = LocalColors.current.onSurface,
        title = {
            if (errorsInBranchList.size < 2) {
                Text(
                    text = stringResource(id = R.string.error),
                    fontSize = LocalTextStyles.current.cardTitle.fontSize
                )
            }
            else {
                Text(
                    text = stringResource(id = R.string.errors),
                    fontSize = LocalTextStyles.current.cardTitle.fontSize
                )
            } },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Log.e("ErrorAlert", errorsInBranchList.toString())

                errorsInBranchList.forEach { errors ->
                    Log.e("ErrorAlert", errors.messages.toString())

                    if (errors.messages.isNotEmpty()) {
                        Text(
                            text = errors.messages.last(),
                            fontSize = LocalTextStyles.current.standard.fontSize
                        )
                    }

                    if (errors.messages.contains(
                            stringResource(id = R.string.error_circuit_is_not_closed)
                    ))
                        return@forEach
                }
            }

        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalColors.current.onSurface,
                    contentColor = LocalColors.current.surface
                ),
                onClick = { calculatorViewModel.hideErrorAlert() }
            ) {
                Text(
                    text = "OK",
                    fontSize = LocalTextStyles.current.standard.fontSize
                )
            }
        }
    )
}


@Preview
@Composable
fun CalcScreenPreview() {

    val calculatorViewModel = CalculatorViewModel(application = Application())
    val calcNav = rememberNavController()
    CalcScreen(
        calculatorViewModel,
        calcNav
    )
}
