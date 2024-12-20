package net.kep.easy_dc.data.calculator

data class BranchResultUI (
    val id: Int = 1,
    val current: Double = 0.0,
    val inputNode: Int = 1,
    val outputNode: Int = 2,
    val summarizedEMF: Double = 0.0,
    val summarizedResistance: Double = 0.0,
)