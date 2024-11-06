package net.kep.dc_guide

import net.kep.dcc.elements.Branch;
import net.kep.dcc.elements.ElectricalCircuit

fun test() {
    // Создаем экземпляры класса Branch
    val branch1 = Branch(1, 2, 123.0, 23.0)
    val branch2 = Branch(1, 2, 132.0, 123.0)
    //val branch3 = Branch(1, 2, 55.1, 0.1)

// Создаем список веток
    val branches = mutableListOf<Branch>()

// Добавляем ветки в список
    branches.add(branch1)
    branches.add(branch2)
    //branches.add(branch3)

    val ec = ElectricalCircuit(branches)

    println(ec.currents)
}

fun main() {
    test()
}
