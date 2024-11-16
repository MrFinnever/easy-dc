package net.kep.dc_guide.model

import android.util.Log
import net.kep.dc_guide.data.calculator.BranchUI
import net.kep.dcc.elements.Branch
import net.kep.dcc.elements.CycleSet
import net.kep.dcc.elements.ElectricalCircuit
import net.kep.dcc.exceptions.CircuitHasBridgesException
import net.kep.dcc.exceptions.CircuitIsNotContinuousException


private fun collectBranches(branchesUI: MutableList<BranchUI>): ArrayList<Branch> {
    val branches = ArrayList<Branch>()

    branchesUI.forEach { branch ->
        val input = branch.input.value.toIntOrNull() ?: 0
        val output = branch.output.value.toIntOrNull() ?: 0

        Log.d("Calculator:collectBranches", "input: $input, output: $output")

        val totalResistance = branch.totalResistance()
        val totalEMF = branch.totalEMF()

        Log.d (
            "Calculator:collectBranches",
            "totalResistance: $totalResistance, totalEMF: $totalEMF"
        )

        branches.add(Branch(input, output, totalEMF, totalResistance))
    }

    Log.d("Calculator:collectBranches", "branches: $branches")

    return branches
}


fun getCurrents(branchesUI: MutableList<BranchUI>): List<Double> {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    Log.d("Calculator:getCurrents", ec.toString())

    return ec.currents
}


fun isCircuitContinuous(branchesUI: MutableList<BranchUI>): Boolean {
    val branches = collectBranches(branchesUI)
    Log.d("Calculator:isCircuitContinuous", branches.toString())

    val ec = ElectricalCircuit(branches)
    var res = false

    try {
        res = ec.isCircuitContinuous
        Log.d("Calculator:isCircuitContinuous", res.toString())
    } catch (e: CircuitIsNotContinuousException) {
        Log.e("Calculator:isCircuitContinuous", e.toString())
    }

    Log.d("Calculator:isCircuitContinuous", res.toString())
    return res
}

fun hasBridges(branchesUI: MutableList<BranchUI>): Boolean {
    val branches = collectBranches(branchesUI)
    Log.d("Calculator:hasBridges", branches.toString())

    val ec = ElectricalCircuit(branches)
    var res = false

    try {
        res = !ec.hasNoBridges()
        Log.d("Calculator:hasBridges", res.toString())
    } catch (e: CircuitHasBridgesException) {
        Log.e("Calculator:hasBridges", e.toString())
    }

    Log.d("Calculator:hasBridges", res.toString())
    return res
}

fun getAllNodes(branchesUI: MutableList<BranchUI>): List<Int> {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    Log.d("Calculator:getAllNodes", ec.allNodes.toString())

    return ec.allNodes
}

fun getConnectedComponentsCount(branchesUI: MutableList<BranchUI>): Int {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    Log.d("Calculator:getConnectedComponentsAmount", ec.connectedComponentsCount.toString())

    return ec.connectedComponentsCount
}

fun getCycleSet(branchesUI: MutableList<BranchUI>): CycleSet? {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    Log.d("Calculator:getCycles", ec.cycleSet.toString())

    return ec.cycleSet
}