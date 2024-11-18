package net.kep.dc_guide.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.kep.dc_guide.R
import net.kep.dc_guide.data.calculator.BranchResultUI
import net.kep.dc_guide.data.calculator.solution.BranchInCycle
import net.kep.dc_guide.data.calculator.solution.CycleUI
import net.kep.dc_guide.data.calculator.solution.sle.Cols
import net.kep.dc_guide.data.calculator.solution.sle.FreeFactors
import net.kep.dc_guide.data.calculator.solution.sle.Matrix
import net.kep.dc_guide.data.calculator.solution.sle.Rows
import net.kep.dc_guide.data.calculator.solution.sle.SLEData


@Composable
fun SolutionScreen(
    branches: List<BranchResultUI>,
    listOfNodes: MutableList<Int>,
    components: MutableIntState,
    listOfCycles: List<CycleUI>,
    sle: SLEData,
    contourCurrents: MutableList<Double>
) {
    Log.d("SolutionScreen:SolutionScreen", "branches: $branches")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        StepOne(
            branches = branches,
            modifier = Modifier
                .fillMaxWidth()
        )
        StepTwo(
            list = listOfNodes,
            modifier = Modifier
                .fillMaxWidth()
        )
        StepThree(
            amount = components,
            modifier = Modifier
                .fillMaxWidth()
        )
        StepFour(
            modifier = Modifier
                .fillMaxWidth()
        )
        StepFive(
            cycles = listOfCycles,
            modifier = Modifier
                .fillMaxWidth()
        )
        StepSix(
            sle = sle,
            modifier = Modifier
                .fillMaxWidth()
        )
        StepSeven(
            contourCurrents = contourCurrents,
            modifier = Modifier
                .fillMaxWidth()
        )
        Result(
            branches = branches,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun StepOne(
    branches: List<BranchResultUI>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.step_1),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )

        branches.forEachIndexed { index, branchResultUI ->
            Log.d(
                "SolutionScreen:StepOne",
                "index: $index, branchResultUI: $branchResultUI"
            )

            BranchSolutionCard(
                id = index+1,
                branch = branchResultUI,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
private fun BranchSolutionCard(
    id: Int,
    branch: BranchResultUI,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        BranchSolutionLabel(
            branchNumber = id,
            branch = branch,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        
        BranchSolutionParams(
            inputNode = branch.inputNode,
            outputNode = branch.outputNode,
            summarizedEMF = branch.summarizedEMF,
            summarizedResistance = branch.summarizedResistance
        )
    }
}

@Composable
private fun BranchSolutionLabel(
    branchNumber: Int,
    branch: BranchResultUI,
    modifier: Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Text(
            text = stringResource(id = R.string.branch) + " $branchNumber",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 10.dp)
        )

        OutlinedIconButton(
            onClick = {
                val params = "Branch ID = ${branch.id}\n" +
                        "Input Node = ${branch.inputNode}\n" +
                        "Output Node = ${branch.outputNode}\n" +
                        "Summarized EMF = ${branch.summarizedEMF
                            .toString().replace(".", ",")} V\n" +
                        "Summarized Resistance = ${branch.summarizedResistance
                            .toString().replace(".", ",")} Ohm"

                clipboardManager.setText(
                    AnnotatedString(params)
                )
                Toast.makeText(context,
                    context.getString(R.string.copied),
                    Toast.LENGTH_SHORT
                ).show()
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy Current"
            )
        }
    }

}


@Composable
private fun BranchSolutionParams(
    inputNode: Int,
    outputNode: Int,
    summarizedEMF: Double,
    summarizedResistance: Double
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column {
            BranchSolutionInput(
                inputNode = inputNode,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            BranchSolutionOutput(
                outputNode = outputNode,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            BranchSolutionEMF(
                summarizedEMF = summarizedEMF,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            BranchSolutionResistance(
                summarizedResistance = summarizedResistance,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp))
        }
    }
}


@Composable
private fun BranchSolutionInput(
    inputNode: Int,
    modifier: Modifier
) {
    Box (
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.input) + ": $inputNode",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 20.dp)
        )
    }
}


@Composable
private fun BranchSolutionOutput(
    outputNode: Int,
    modifier: Modifier
) {
    Box (
        modifier = modifier
    ) {

        Text(
            text = stringResource(id = R.string.output) + ": $outputNode",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 20.dp)
        )
    }
}


@Composable
private fun BranchSolutionEMF(
    summarizedEMF: Double,
    modifier: Modifier
) {
    val emf = formatDoubleToString(summarizedEMF)
    Box(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.emf) + ": $emf V",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
        )
    }

}


@Composable
private fun BranchSolutionResistance(
    summarizedResistance: Double,
    modifier: Modifier
) {
    val resistance = formatDoubleToString(summarizedResistance)
    Box(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.resistance) + ": $resistance Ohm",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
        )
    }

}


@Composable
private fun StepTwo(
    list: MutableList<Int>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.step_2),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
        ListOfNodes(
            list = list,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
private fun ListOfNodes(
    list: MutableList<Int>,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Log.d("SolutionScreen:ListOfNodes", list.toString())

            if (list.size == 0) {
                Text(
                    text = stringResource(id = R.string.has_no_nodes),
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
            }
            else {
                Text(
                    text =  stringResource(id = R.string.number_of_nodes) + ": ${list.size}",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Nodes(list = list, modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth())

            }
        }
    }
}


@Composable
fun Nodes(
    list: MutableList<Int>,
    modifier: Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.node_numbers),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.FixedSize(48.dp),
            modifier = Modifier
                .padding(10.dp)
                .heightIn(max = 400.dp)
        ) {
            items(list.size) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp) // Размер квадратика
                            .padding(4.dp)
                            .background(
                                color = Color.Gray,
                                shape = CircleShape
                            )
                    )
                    Text(
                        text = "${list[it]}",
                        color = Color.White
                    )
                }

            }
        }
    }
}


@Composable
private fun StepThree(
    amount: MutableIntState,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.step_3),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
        AmountOfComponents(
            amount = amount,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
private fun AmountOfComponents(
    amount: MutableIntState,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            if (amount.intValue == 0) {
                Text(
                    text = stringResource(id = R.string.has_no_components),
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
            }
            else {
                Text(
                    text = stringResource(id = R.string.connected_components)
                            + ": ${amount.intValue}",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 10.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.FixedSize(48.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .heightIn(max = 400.dp)
                ) {
                    items(amount.intValue){
                        Box(
                            modifier = Modifier
                                .size(48.dp) // Задаем размер квадратика
                                .padding(4.dp)
                                .background(
                                    color = Color.Gray,
                                    shape = MaterialTheme.shapes.medium
                                )
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun StepFour(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
        text = stringResource(id = R.string.step_4),
        fontSize = 22.sp,
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
    )
        ChecksCard()
    }
}


@Composable
fun ChecksCard() {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.checks),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 10.dp)
            )
            CircuitContinuous()
            HasNoBridges()
        }
    }
}


@Composable
private fun CircuitContinuous() {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.circuit_is_closed),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xff4ccf00))
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "", tint = Color.White)
            }
        }
    }
}


@Composable
private fun HasNoBridges() {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.circuit_has_no_bridges),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xff4ccf00))
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "", tint = Color.White)
            }
        }
    }
}


@Composable
private fun StepFive(
    cycles: List<CycleUI>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.step_5),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
        CycleCards(
            cycles = cycles,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
private fun CycleCards(
    cycles: List<CycleUI>,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        if (cycles[0].branches.isEmpty()) {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.no_cycles_to_solve),
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
        else {
            Column(
                modifier = Modifier

                    .fillMaxWidth()
            ) {
                for (i in cycles.indices) {
                    CycleCard(
                        cycleIndex = i,
                        cycle = cycles[i],
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                            .fillMaxWidth()
                    )
                }
            }

        }
    }
}


@Composable
private fun CycleCard(
    cycleIndex: Int,
    cycle: CycleUI,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.cycle) + " ${cycleIndex+1}",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            )

            Log.d("SolutionScreen:CycleCard",
                "Cycle Index: $cycleIndex\n" +
                    "Cycle: $cycle")

            cycle.branches.forEach { branch  ->
                CycleComponentCard(
                    branchID = branch.branchId,
                    isInverted = branch.isInverted,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Composable
private fun CycleComponentCard(
    branchID: Int,
    isInverted: Boolean,
    modifier: Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.branch) + " $branchID",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.inverted) + ":",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )

                if (isInverted) {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp, top = 10.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF81C784))
                    ) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "", tint = Color.White)
                    }
                }
                else {
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp, top = 10.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFEF9A9A))
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
private fun StepSix(
    sle: SLEData,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.step_6),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
        SLECard(
            sle = sle,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
    }
}


@Composable
private fun SLECard(
    sle: SLEData,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.SLE),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            )
            SLEText(
                sle = sle,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            )
        }
    }
}

@Composable
private fun SLEText(
    sle: SLEData,
    modifier: Modifier
) {
    val rowCount = sle.matrix.value.rows.size
    val dividerHeight = (rowCount * 32).dp
    Row(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .padding(start = 2.dp, end = 10.dp)
                .height(dividerHeight)
        ) {
            val height = size.height
            drawLine(
                color = Color.Black,
                start = Offset(x = size.width / 2, y = 0f),
                end = Offset(x = size.width / 2, y = height),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )
        }


        Column(modifier = Modifier.fillMaxWidth()) {

            sle.matrix.value.rows.forEachIndexed { rowIndex, row ->
                val equation = buildString {
                    var isFirst = false
                    row.col.forEachIndexed { colIndex, coefficient ->

                        if (colIndex == 0) isFirst = true

                        if (coefficient != 0.0) {
                            Log.d(
                                "SolutionScreen:SLEText",
                                "RowIndex: $rowIndex, Row: $row,\n" +
                                        "ColIndex: $colIndex, Coefficient: $coefficient"
                            )

                            append(formatCoefficient(coefficient, colIndex, isFirst))
                            append("×i${colIndex + 1}")

                            isFirst = false
                        }
                    }

                    val freeFactor = sle.freeFactors.value[rowIndex]
                    if (freeFactor < 0)
                        append(" = −${formatNumber(-freeFactor)}")
                    else
                        append(" = ${formatNumber(freeFactor)}")
                }

                Text(
                    text = equation,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun formatCoefficient(coefficient: Double, colIndex: Int, isFirst: Boolean): String {
    val result = when {
        colIndex == 0 -> {
            when {
                coefficient < 0 -> "−${formatNumber(-coefficient)}"
                coefficient > 0 ->  formatNumber(coefficient)
                else -> ""
            }
        }
        colIndex != 0 -> {
            when {
                (coefficient < 0 && isFirst) -> "−${formatNumber(-coefficient)}"
                (coefficient > 0 && isFirst) -> formatNumber(coefficient)
                coefficient < 0 -> " − ${formatNumber(-coefficient)}"
                coefficient > 0 -> " + ${formatNumber(coefficient)}"
                else -> ""
            }
        }
        else -> ""
    }

    return result
}


// Функция для форматирования числа, убирая .0, если число целое
@Composable
private fun formatNumber(number: Double): String {
    return if (number % 1.0 == 0.0) {
        // Убираем .0 у чисел
        number.toInt().toString()
    } else {
        "%.1f".format(number).replace(".", ",")
    }
}


@Composable
private fun StepSeven(
    contourCurrents: MutableList<Double>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.step_7),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
        ContourCurrentsCard(
            contourCurrents = contourCurrents,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
private fun ContourCurrentsCard(
    contourCurrents: MutableList<Double>,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.loop_currents),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 10.dp)
            )
            
            contourCurrents.forEach { current ->
                ContourCurrent(
                    current = current,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Composable
private fun ContourCurrent(
    current: Double,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    ) {
        Text(
            text = "I = ${formatDoubleToString(current)} " + stringResource(id = R.string.ampere),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}


@Composable
fun Result(
    branches: List<BranchResultUI>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(id = R.string.result),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )

        branches.forEach {branch ->
            ResultCard(
                branch = branch,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SolutionScreenPreview() {
    val branches = listOf(BranchResultUI())
//    val listOfNodes = mutableListOf(1, 3, 4, 5, 8, 23)
//    val amountOfComponents = remember { mutableIntStateOf(3) }
//    val listOfCycles = listOf(CycleUI())
//    val contCurrents = mutableListOf(1.2, 23.4, 4.0)

//    SolutionScreen(
//        branches,
//        listOfNodes,
//        amountOfComponents,
//        listOfCycles,
//        SLEData(
//            Matrix(
//                Rows(
//                    listOf(Cols(listOf()))
//                )
//            ),
//            FreeFactors(listOf())
//        ),
//        contCurrents
//    )

    Result(branches = branches, modifier = Modifier.fillMaxWidth())
}


@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepOnePreview() {
    val branches = listOf(BranchResultUI())

    StepOne(branches = branches, modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepTwoPreview() {
    val listOfNodes = mutableListOf(1, 3, 4, 5, 8, 23)

    StepTwo(list = listOfNodes, modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepThreePreview() {
    val amountOfComponents = remember { mutableIntStateOf(3) }

    StepThree(amount = amountOfComponents, modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepFourPreview() {

    StepFour(modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepFivePreview() {
    val branchesSolution = listOf(BranchInCycle(), BranchInCycle(2, true))
    val listOfCycles = listOf(CycleUI(branchesSolution))

    StepFive(
        cycles = listOfCycles,
        modifier = Modifier
            .fillMaxWidth()
    )

}


@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepSixPreview() {

    StepSix(
        sle = SLEData(
            Matrix(
                Rows(
                    listOf(
                        Cols(listOf(-1.0, 0.0, 3.0)),
                        Cols(listOf(-4.0, -5.0, 6.0)),
                        Cols(listOf(-4.0, -5.0, 6.0)),
                        Cols(listOf(12.0, -5.0, 6.0))
                    )
                )
            ),
            FreeFactors(listOf(1.0, -2.0, 3.0, -4.0))
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepSevenPreview() {

    StepSeven(
        contourCurrents = mutableListOf(24.3, 324.4),
        modifier = Modifier.fillMaxWidth()
    )
}