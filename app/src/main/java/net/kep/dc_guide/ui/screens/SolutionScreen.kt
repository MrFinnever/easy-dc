package net.kep.dc_guide.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.kep.dc_guide.R
import net.kep.dc_guide.data.BranchResultUI


@Composable
fun SolutionScreen(
    branches: List<BranchResultUI>
) {
    Log.d("SolutionScreen:SolutionScreen", "branches: $branches")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        StepOne(branches)
    }

}

@Composable
fun StepOne(
    branches: List<BranchResultUI>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
fun BranchSolutionCard(
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
fun BranchSolutionLabel(
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
fun BranchSolutionParams(
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
fun BranchSolutionInput(
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
fun BranchSolutionOutput(
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
fun BranchSolutionEMF(
    summarizedEMF: Double,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = "EMF: $summarizedEMF V",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
        )
    }

}


@Composable
fun BranchSolutionResistance(
    summarizedResistance: Double,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = "Resistance: $summarizedResistance Ohm",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
        )
    }

}


@Preview(showBackground = true)
@Composable
fun SolutionScreenPreview() {
    val branches = listOf(BranchResultUI())
    SolutionScreen(
        branches
    )
}