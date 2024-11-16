package net.kep.dc_guide.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
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


@Composable
fun SolutionScreen(
    branches: List<BranchResultUI>,
    listOfNodes: MutableList<Int>,
    components: MutableIntState,
    listOfCycles: List<CycleUI>
) {
    Log.d("SolutionScreen:SolutionScreen", "branches: $branches")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(bottom = 80.dp)
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
            text ="Step 1 - Collect Branches",
            fontSize = 22.sp,
            modifier = Modifier.padding(20.dp)
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
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
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
            text = "Input: $inputNode",
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
            text = "Output: $outputNode",
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
    val emf = formatValue(summarizedEMF)
    Box(
        modifier = modifier
    ) {
        Text(
            text = "EMF: $emf V",
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
    val resistance = formatValue(summarizedResistance)
    Box(
        modifier = modifier
    ) {
        Text(
            text = "Resistance: $resistance Ohm",
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
            text ="Step 2",
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
                    text = "В цепи нет узлов",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
            }
            else {
                Text(
                    text = "Количество узлов: ${list.size}",
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
                text = "Номера узлов",
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
            text ="Step 3",
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
                    text = "В цепи нет компонент",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
            }
            else {
                Text(
                    text = "Компонент связанности: ${amount.intValue}",
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
        text ="Step 4",
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
                text = "Проверки",
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
                text = "Цепь замкнута",
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
                text = "Цепь без мостов",
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
            text ="Step 5",
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
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Нет циклов для решения",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
        else {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                for (i in cycles.indices) {
                    CycleCard(
                        cycleIndex = i,
                        cycle = cycles[i],
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 10.dp)
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
                text = "Цикл ${cycleIndex+1}",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
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
                text = "Ветвь $branchID",
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
                    text = "Инвертирована:",
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


@Preview(showBackground = true)
@Composable
private fun SolutionScreenPreview() {
    val branches = listOf(BranchResultUI())
    val listOfNodes = mutableListOf(1, 3, 4, 5, 8, 23)
    val amountOfComponents = remember { mutableIntStateOf(3) }
    val listOfCycles = listOf(CycleUI())

    SolutionScreen(
        branches,
        listOfNodes,
        amountOfComponents,
        listOfCycles
    )
}


@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepOnePreview() {
    val branches = listOf(BranchResultUI())
    val listOfNodes = mutableListOf(1, 3, 4, 5, 8, 23)
    val amountOfComponents = remember { mutableIntStateOf(3) }

    StepOne(branches = branches, modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepTwoPreview() {
    val branches = listOf(BranchResultUI())
    val listOfNodes = mutableListOf(1, 3, 4, 5, 8, 23)
    val amountOfComponents = remember { mutableIntStateOf(3) }

    StepTwo(list = listOfNodes, modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepThreePreview() {
    val branches = listOf(BranchResultUI())
    val listOfNodes = mutableListOf(1, 3, 4, 5, 8, 23)
    val amountOfComponents = remember { mutableIntStateOf(3) }

    StepThree(amount = amountOfComponents, modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepFourPreview() {
    val branches = listOf(BranchResultUI())
    val listOfNodes = mutableListOf(1, 3, 4, 5, 8, 23)
    val amountOfComponents = remember { mutableIntStateOf(3) }

    StepFour(modifier = Modifier.fillMaxWidth())

}

@Preview(showBackground = true)
@Composable
private fun SolutionScreenStepFivePreview() {
    val branchesSolution = listOf<BranchInCycle>(BranchInCycle(), BranchInCycle(2, true))
    val listOfCycles = listOf(CycleUI(branchesSolution))

    StepFive(
        cycles = listOfCycles,
        modifier = Modifier
            .fillMaxWidth()
    )
}