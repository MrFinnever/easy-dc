package net.kep.dc_guide.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.kep.dc_guide.ui.viewmodel.BranchViewModel


@Composable
fun SolutionScreen(
    branchViewModel: BranchViewModel = viewModel()
) {
    val branches by branchViewModel.result.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(bottom = 80.dp)
        ) {
            branches.forEach {branch ->
                ResultCard(
                    branch = branch,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
                )
            }
        }
    }
}



@Preview
@Composable
fun SolutionScreenPreview() {
    val branchViewModel = BranchViewModel()
    SolutionScreen(
        branchViewModel
    )
}