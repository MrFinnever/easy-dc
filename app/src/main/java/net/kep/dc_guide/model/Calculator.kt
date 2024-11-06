package net.kep.dc_guide.model

import android.util.Log
import net.kep.dc_guide.data.BranchUI
import net.kep.dcc.elements.Branch
import net.kep.dcc.elements.ElectricalCircuit

//private fun collectBranches(branchesUI: MutableList<BranchUI>): ArrayList<Branch> {
//    var branches = ArrayList<Branch>()
//
//    branchesUI.forEach { branch ->
//        val input = branch.input.value.toIntOrNull() ?: 0
//        val output = branch.output.value.toIntOrNull() ?: 0
//        val totalResistance = branch.totalResistance()
//        val totalEMF = branch.totalEMF()
//
//        branches = branches.apply {
//            add(
//                Branch(
//                    input,
//                    output,
//                    totalEMF,
//                    totalResistance
//                )
//            )
//        }
//    }
//
//    return branches
//}


private fun collectBranches(branchesUI: MutableList<BranchUI>): ArrayList<Branch> {
    val branches = ArrayList<Branch>()

    branchesUI.forEach { branch ->
        val inputValue = branch.input.value
        val outputValue = branch.output.value

        // Логирование значений
        Log.d("BranchInputOutput", "Input: $inputValue, Output: $outputValue")
        val input = branch.input.value.toIntOrNull() ?: 0
        val output = branch.output.value.toIntOrNull() ?: 0

        Log.d("BranchInputOutput", "Input: $input, Output: $output")

        val totalResistance = branch.totalResistance()
        val totalEMF = branch.totalEMF()

        // Добавляем проверку для totalResistance и totalEMF
        if (totalResistance <= 0) {
            Log.e("ERROR","Общее сопротивление недопустимо: $totalResistance")
        }
        if (totalEMF < 0) {  // Предположим, что ЭДС не может быть отрицательной
            Log.e("ERROR","Общее ЭДС недопустимо: $totalEMF")
        }

        branches.add(Branch(input, output, totalEMF, totalResistance))
    }

    return branches
}


fun getCurrents(branchesUI: MutableList<BranchUI>): List<Double> {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    return ec.currents
}

fun isCircuitContinuous(branchesUI: MutableList<BranchUI>): Boolean {
    val branches = collectBranches(branchesUI)
    Log.d("Calculator", branches.toString())

    val ec = ElectricalCircuit(branches)

    var res = false

    try {
        res = ec.isCircuitContinuous
        Log.d("Calculator", res.toString())
    } catch (e: Exception) {
        Log.e("ERROR", e.toString())
        res = ec.isCircuitContinuous
        Log.d("Calculator", res.toString())
    }

    return res
}