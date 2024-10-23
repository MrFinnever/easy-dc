package net.kep.dc_guide.data

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Branch(
    var id: Int = 1,
    var input: MutableState<String> = mutableStateOf(""),
    var output: MutableState<String> = mutableStateOf(""),
    var resistors: SnapshotStateList<String> = mutableStateListOf(""),
    var emf: SnapshotStateList<String> = mutableStateListOf("")
) {
    fun totalResistance(): Double {
        return resistors
            .mapNotNull { it.toDoubleOrNull() }
            .sum()
    }

    fun totalEMF(): Double {
        return emf
            .mapNotNull { it.toDoubleOrNull() }
            .sum()
    }
}
