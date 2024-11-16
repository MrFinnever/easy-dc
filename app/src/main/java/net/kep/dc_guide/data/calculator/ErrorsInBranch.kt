package net.kep.dc_guide.data.calculator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class ErrorsInBranch(
    var id: Int = 1,
    var isInputError: MutableState<Boolean> = mutableStateOf(false),
    var isOutputError: MutableState<Boolean> = mutableStateOf(false),
    var isResistorsError: SnapshotStateList<Boolean> = mutableStateListOf(false),
    var isEMFError: SnapshotStateList<Boolean> = mutableStateListOf(false),
    var hasNoBridges: MutableState<Boolean> = mutableStateOf(false),
    var isCircuitNotContinuous: MutableState<Boolean> = mutableStateOf(false),
    var messages: MutableSet<String> = mutableSetOf(),
)
