package net.kep.dc_guide.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class BranchUI(
    var id: Int = 1,
    var input: MutableState<String> = mutableStateOf(""),
    var output: MutableState<String> = mutableStateOf(""),
    var resistors: SnapshotStateList<String> = mutableStateListOf(""),
    var emf: SnapshotStateList<String> = mutableStateListOf("")
) {
    private fun String.toDoubleWithCommaSupport(): Double? {
        return this.replace(",", ".").toDoubleOrNull()
    }

    fun totalResistance(): Double {
        return resistors.sumOf { it.toDoubleWithCommaSupport() ?: 0.0 }
    }

    fun totalEMF(): Double {
        return emf.sumOf { it.toDoubleWithCommaSupport() ?: 0.0 }
    }
}
