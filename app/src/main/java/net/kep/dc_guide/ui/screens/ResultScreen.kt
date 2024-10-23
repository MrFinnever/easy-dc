package net.kep.dc_guide.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.kep.dc_guide.ui.viewmodel.BranchViewModel


@Composable
fun ResultScreen(
    branchViewModel: BranchViewModel = viewModel(),
    calcNavCon: NavController
) {
    val branches by branchViewModel.branches.collectAsState()

    Scaffold(
        topBar = {
            ResultTopAppBar(calcNavCon = calcNavCon)
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            branches.forEach {branch ->
                Text(text = "${branch.totalResistance()}")
                Text(text = "${branch.totalEMF()}")
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
                text = "Результат",
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