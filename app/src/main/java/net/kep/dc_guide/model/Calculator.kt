package net.kep.dc_guide.model

import net.kep.dc_guide.data.BranchUI
import net.kep.dcc.elements.Branch
import net.kep.dcc.elements.ElectricalCircuit

private fun collectBranches(branchesUI: MutableList<BranchUI>): ArrayList<Branch> {
    var branches = ArrayList<Branch>()

    branchesUI.forEach { branch ->
        val input = branch.input.value.toIntOrNull() ?: 0
        val output = branch.output.value.toIntOrNull() ?: 0
        val totalResistance = branch.totalResistance()
        val totalEMF = branch.totalEMF()

        branches = branches.apply {
            add(
                Branch(
                    input,
                    output,
                    totalEMF,
                    totalResistance
                )
            )
        }
    }

    return branches
}

fun getCurrents(branchesUI: MutableList<BranchUI>): List<Double> {
    val branches = collectBranches(branchesUI)
    val ec = ElectricalCircuit(branches)

    return ec.currents
}