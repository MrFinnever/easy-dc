package net.kep.dc_guide

import net.kep.dcc.elements.Branch
import net.kep.dcc.elements.ElectricalCircuit
import net.kep.dcc.util.SLE

fun test() {
    // Создаем экземпляры класса Branch
    val branch1 = Branch(1, 2, 123.0, 23.0)
    val branch2 = Branch(1, 2, 124.0, 123.0)
    val branch3 = Branch(1, 2, 55.1, 0.1)
    val branch4 = Branch(1, 2, 1236.0, 234.0)
    val branch5 = Branch(1, 2, 13.0, 12643.0)
    val branch6 = Branch(1, 2, 545.1, 0.1564)

// Создаем список веток
    val branches = mutableListOf<Branch>()

// Добавляем ветки в список
    branches.add(branch1)
    branches.add(branch2)
    branches.add(branch3)
    branches.add(branch4)
    branches.add(branch5)
    branches.add(branch6)

    val ec = ElectricalCircuit(branches)

    val cycleSet = ec.cycleSet

    val sle = SLE(cycleSet, ec)

    val matrix = sle.matrixSLE
    val freeFactors = sle.vectorFreeFactors

    // Преобразование матрицы в список списков
    val matrixList = List(matrix.numRows) { row ->
        List(matrix.numCols) { col ->
            matrix.get(row, col)
        }
    }

    // Преобразование вектора свободных членов в список
    val freeFactorsList = List(freeFactors.numRows) { row ->
        freeFactors.get(row, 0)
    }

    println(matrixList)
    println(freeFactorsList)
    println(sle)
}

fun main() {
    test()
}
