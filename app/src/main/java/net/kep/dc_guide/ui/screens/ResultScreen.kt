package net.kep.dc_guide.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.kep.dc_guide.R
import net.kep.dc_guide.data.BranchResultUI
import net.kep.dc_guide.ui.viewmodel.BranchViewModel
import kotlin.math.absoluteValue


@Composable
fun ResultScreen(
    branchViewModel: BranchViewModel = viewModel(),
    calcNavCon: NavController
) {
    val branches by branchViewModel.result.collectAsState()
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Ответ", "Решение")


    Scaffold(
        topBar = {
            ResultTopAppBar(calcNavCon = calcNavCon)
        },
        floatingActionButton = {
            CopyAllFAB(branches)
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(bottom = 80.dp)
        ) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                0 -> {
                    branches.forEach {branch ->
                        ResultCard(
                            branch = branch,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
                        )
                    }
                }
                else -> {
                    branches.forEach {branch ->
                        ResultCard(
                            branch = branch,
                            modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)
                        )
                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultTopAppBar(
    calcNavCon: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.result),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
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
fun ResultCard(
    branch: BranchResultUI,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            ResultCardLabel(
                branchNumber = branch.id,
                modifier = Modifier.fillMaxWidth()
            )
            DCValueCard(
                dcValue = branch.current,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}


@Composable
fun ResultCardLabel(
    branchNumber: Int,
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
            color = MaterialTheme.colorScheme.onSurface
        )
        OutlinedIconButton(
            onClick = {
                clipboardManager.setText(
                    AnnotatedString(branchNumber.toString())
                )
                Toast.makeText(context,
                    context.getString(R.string.copied),
                    Toast.LENGTH_SHORT
                ).show()
            },
            shape = MaterialTheme.shapes.large
        ) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy Current"
            )
        }
    }
}


@Composable
fun DCValueCard(
    dcValue: Double,
    modifier: Modifier
) {
    fun formatValue(dcValue: Double): String {
        val newValue = if (dcValue < 0.0) "−${dcValue.absoluteValue}" else dcValue.toString()
        return newValue.replace(".", ",")
    }

    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (dcValue < 0) dcValue.toString().replace("-", "－")
        Text(
            text = "I = " + formatValue(dcValue),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun CopyAllFAB(
    branchUIS: List<BranchResultUI>

) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    ExtendedFloatingActionButton(

        onClick = {
            val allBranchCurrents = branchUIS.joinToString(separator = "\n") { branch ->
                "I${branch.id} = ${branch.id} A"
            }
            clipboardManager.setText(AnnotatedString(allBranchCurrents))
            Toast.makeText(context,
                context.getString(R.string.all_copied),
                Toast.LENGTH_SHORT
            ).show()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CopyAll,
                contentDescription = "Copy all")
            Text(
                text = stringResource(id = R.string.copy_all),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}


@Preview
@Composable
fun ResultScreenPreview() {
    val branchViewModel = BranchViewModel()
    val calcNav = rememberNavController()
    ResultScreen(
        branchViewModel,
        calcNav
    )
}