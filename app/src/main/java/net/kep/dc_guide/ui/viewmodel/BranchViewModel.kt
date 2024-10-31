package net.kep.dc_guide.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.kep.dc_guide.data.BranchResultUI
import net.kep.dc_guide.data.BranchUI
import net.kep.dc_guide.model.getCurrents


class BranchViewModel: ViewModel() {

    //========== Variables ==========//
    private val _branches = MutableStateFlow(mutableListOf(BranchUI()))
    val branches: StateFlow<MutableList<BranchUI>> = _branches.asStateFlow()

    private val _result = MutableStateFlow(mutableListOf(BranchResultUI()))
    val result: StateFlow<List<BranchResultUI>> = _result.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow(mutableSetOf<String>())
    val errorMessage: StateFlow<Set<String>> = _errorMessage.asStateFlow()



    //========== Methods ==========//
    fun addBranch() {
        val newBranchUI = BranchUI(id = _branches.value.size + 1)
        _branches.value = _branches.value.toMutableList().apply { add(newBranchUI) }
    }

    fun removeBranch(index: Int) {
        if (index > 0 && index < _branches.value.size) {
            _branches.value = _branches.value.toMutableList().apply { removeAt(index) }
            reindexBranch()
        }
    }

    private fun reindexBranch() {
        _branches.value = _branches.value
            .mapIndexed { i, branch ->
                branch.copy(id = i + 1)
            }.toMutableList()
    }

    fun checkBranches(calcNavCon: NavController) {
        resetError()
        _errorMessage.value = mutableSetOf()

        _branches.value.forEachIndexed { index, branch ->

            if (branch.input.value == "") {
                _errorMessage.value = _errorMessage.value.toMutableSet()
                    .apply { add("Ветвь ${index+1}. Не указан начальный узел") }
            }

            if (branch.output.value == "") {
                _errorMessage.value = _errorMessage.value.toMutableSet()
                    .apply { add("Ветвь ${index+1}. Не указан конечный узел") }
            }

        }

        if (_errorMessage.value.isNotEmpty())
            setError()
        else
            calcNavCon.navigate(route = "result")
    }

    fun calculate() {
        _branches.value.forEachIndexed { index, branch ->
            println("Branch ${index + 1}:" +
                    "Node 1 = ${branch.input.value}," +
                    "Node 2 = ${branch.output.value}," +
                    "EMF = ${branch.emf}," +
                    "R = ${branch.resistors}")
        }

        try {
            val res = getCurrents(_branches.value)

            val results = res.mapIndexed { index, current ->
                BranchResultUI(
                    index+1,
                    current
                )
            }.toMutableList()
            _result.value = results
        } catch (e: Exception) {
            println("Error: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }

    private fun setError() {
        _isError.value = true
    }

    fun resetError() {
        _isError.value = false
    }
}