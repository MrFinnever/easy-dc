package net.kep.easy_dc.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.kep.easy_dc.R
import net.kep.easy_dc.data.calculator.BranchResultUI
import net.kep.easy_dc.ui.theme.LocalColors
import net.kep.easy_dc.ui.theme.LocalTextStyles
import net.kep.easy_dc.ui.viewmodel.CalculatorViewModel
import kotlin.math.absoluteValue


enum class Tabs {
    RESULT,
    SOLUTION
}


@Composable
fun ResultScreen(
    calculatorViewModel: CalculatorViewModel = viewModel(),
    calcNavCon: NavController
) {
    val branches by calculatorViewModel.result.collectAsState()
    val listOfNodes by calculatorViewModel.allNodes.collectAsState()
    val amountOfComponents by calculatorViewModel.amountOfComponents.collectAsState()
    val listOfCycles by calculatorViewModel.allCycles.collectAsState()
    val sle by calculatorViewModel.sle.collectAsState()
    val loopCurrents by calculatorViewModel.loopCurrents.collectAsState()

    var selectedTab by remember { mutableStateOf(Tabs.RESULT) }
    val tabs = listOf(
        stringResource(id = R.string.result),
        stringResource(id = R.string.solution)
    )

    Scaffold(
        topBar = {
            ResultTopAppBar(calcNavCon = calcNavCon)
        },
        floatingActionButton = {
            if (selectedTab == Tabs.RESULT) {
                CopyAllFAB(
                    listBranchResult = branches,
                    selectedTab = selectedTab
                )
            }
        },
        containerColor = LocalColors.current.background
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            TabRow(
                containerColor = LocalColors.current.surface,
                contentColor = LocalColors.current.onSurface,
                selectedTabIndex = selectedTab.ordinal,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                        color = LocalColors.current.onSurface,
                        height = 2.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                fontSize = LocalTextStyles.current.standard.fontSize,
                                fontWeight = FontWeight.Normal
                            ) },
                        selected = selectedTab.ordinal == index,
                        onClick = { selectedTab = Tabs.entries[index] }
                    )
                }
            }

            when (selectedTab) {
                Tabs.RESULT -> {
                    ResultCards(branches)
                }
                Tabs.SOLUTION -> {
                    SolutionScreen(
                        branches,
                        listOfNodes,
                        amountOfComponents,
                        listOfCycles,
                        sle,
                        loopCurrents
                    )
                }
            }

        }
    }
}


@Composable
fun ResultCards(branches: List<BranchResultUI>) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        branches.forEach {branch ->
            ResultCard(
                branch = branch,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultTopAppBar(
    calcNavCon: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.result),
                fontSize = LocalTextStyles.current.navigationTitle.fontSize
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
fun ResultCard(
    branch: BranchResultUI,
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
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 20.dp)
        ) {
            ResultCardLabel(
                branch = branch,
                modifier = Modifier.fillMaxWidth()
            )
            DCValueCard(
                dcValue = branch.current,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
                    .padding(horizontal = 10.dp)
            )
        }
    }
}


@Composable
private fun ResultCardLabel(
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
            text = stringResource(id = R.string.branch) + " ${branch.id}",
            fontSize = LocalTextStyles.current.cardTitle.fontSize,
            color = LocalColors.current.onSurface
        )
        OutlinedIconButton(
            onClick = {
                val current = branch.current.toString().replace(".", ",")

                clipboardManager.setText(
                    AnnotatedString(current)
                )
                Toast.makeText(context,
                    context.getString(R.string.copied),
                    Toast.LENGTH_SHORT
                ).show()
            },
            colors =  IconButtonDefaults.iconButtonColors(
                contentColor = LocalColors.current.onSurface
            ),
            border = BorderStroke(1.dp, LocalColors.current.onSurface),
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
private fun DCValueCard(
    dcValue: Double,
    modifier: Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.resultCardAccent
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (dcValue < 0) dcValue.toString().replace("-", "－")
        Text(
            text = "I = " + formatDoubleToString(dcValue)
                    + " " + stringResource(id = R.string.ampere),
            fontSize = LocalTextStyles.current.cardTitle.fontSize,
            color = LocalColors.current.onResultCardAccent,
            modifier = Modifier
                .padding(10.dp)
                .padding(start = 10.dp)
                .horizontalScroll(rememberScrollState())
        )
    }
}


@Composable
private fun CopyAllFAB(
    listBranchResult: List<BranchResultUI>,
    selectedTab: Tabs
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    ExtendedFloatingActionButton(
        containerColor = LocalColors.current.onSurface,
        contentColor = LocalColors.current.surface,
        onClick = {
            if (selectedTab == Tabs.RESULT) {
                    val allBranchCurrents = listBranchResult
                        .joinToString(separator = "\n") { branch ->
                            val current = branch.current.toString()
                                .replace(".", ",")

                            if (branch.current < 0) current
                                .replace("-", "－")

                            "I${branch.id} = $current A"
                    }
                    clipboardManager.setText(AnnotatedString(allBranchCurrents))
                }
            Toast.makeText(context, context.getString(R.string.all_copied), Toast.LENGTH_SHORT).show()
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
                fontSize = LocalTextStyles.current.cardTitle.fontSize,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}

@Composable
fun formatDoubleToString(value: Double): String {
    var newValue = if (value < 0.0) "−${value.absoluteValue}" else value.toString()
    newValue = newValue.replace(".", ",")

    if (newValue.endsWith(",0"))
        newValue = newValue.dropLast(2)

    return newValue
}


@Preview
@Composable
private fun ResultScreenPreview() {
    val calculatorViewModel = CalculatorViewModel(application = Application())
    val calcNav = rememberNavController()
    ResultScreen(
        calculatorViewModel,
        calcNav
    )
}