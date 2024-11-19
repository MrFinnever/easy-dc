package net.kep.easy_dc.model

import android.util.Log
import net.kep.easy_dc.data.calculator.BranchUI
import net.kep.easy_dc.data.calculator.solution.sle.Cols
import net.kep.easy_dc.data.calculator.solution.sle.FreeFactors
import net.kep.easy_dc.data.calculator.solution.sle.Matrix
import net.kep.easy_dc.data.calculator.solution.sle.Rows
import net.kep.easy_dc.data.calculator.solution.sle.SLEData
import net.kep.dcc.elements.Branch
import net.kep.dcc.elements.CycleSet
import net.kep.dcc.elements.ElectricalCircuit
import net.kep.dcc.exceptions.CircuitHasBridgesException
import net.kep.dcc.exceptions.CircuitIsNotContinuousException
import net.kep.dcc.util.SLE

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
    var ec: ElectricalCircuit? = null
    var res = false


    try {
        // Попытка создать объект ElectricalCircuit
        ec = ElectricalCircuit(branches)
    } catch (e: CircuitHasBridgesException) {
        // Обработка ошибки о мостах и продолжение выполнения
        Log.e("Calculator:isCircuitContinuous", "${e.message}")
    }
    if (ec != null) {
        try {
            res = ec.isCircuitContinuous
            Log.w("Calculator:isCircuitContinuous", res.toString())
        } catch (e: CircuitIsNotContinuousException) {
            Log.e("Calculator:isCircuitContinuous", "${e.message}")
        }
    } else {
        Log.e("Calculator:isCircuitContinuous", "Не удалось создать объект ElectricalCircuit")
    }

    Log.w("Calculator:isCircuitContinuous", res.toString())
    return res
}

fun hasBridges(branchesUI: MutableList<BranchUI>): Boolean {
    val branches = collectBranches(branchesUI)
    Log.d("Calculator:hasBridges", branches.toString())
    var ec: ElectricalCircuit? = null
    var res = false

    try {
        ec = ElectricalCircuit(branches)
    } catch (e: CircuitHasBridgesException) {
        res = true
        Log.e("Calculator:hasBridges", "${e.message}")
    }

    Log.w("Calculator:hasBridges", res.toString())
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

fun getCycleSet(branchesUI: MutableList<BranchUI>): CycleSet {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    Log.d("Calculator:getCycles", ec.cycleSet.toString())

    return ec.cycleSet
}

fun getSLEMatrix(branchesUI: MutableList<BranchUI>): SLEData {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)
    val cycleSet = ec.cycleSet
    val sle = SLE(cycleSet, ec)

    val matrixSLE = sle.matrixSLE
    val freeFactorsSLE = sle.vectorFreeFactors

    val matrixRows = List(matrixSLE.numRows) { row ->
        Cols(col = List(matrixSLE.numCols) { col ->
            matrixSLE.get(row, col)
        })
    }
    val rows = Rows(rows = matrixRows)

    val freeFactorsList = List(freeFactorsSLE.numRows) { row ->
        freeFactorsSLE.get(row, 0)
    }

    val matrix = Matrix(rows)
    val freeFactors = FreeFactors(freeFactorsList)

    return SLEData(matrix, freeFactors)
}

fun getContCurrents(branchesUI: MutableList<BranchUI>): List<Double> {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    return ec.contourCurrents
}