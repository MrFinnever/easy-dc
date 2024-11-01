package net.kep.dc_guide.ui.viewmodel
import android.content.Context
import androidx.compose.runtime.toMutableStateList
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.kep.dc_guide.R
import net.kep.dc_guide.data.BranchResultUI
import net.kep.dc_guide.data.BranchUI
import net.kep.dc_guide.data.ErrorBranch
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

    private val _errorBranches = MutableStateFlow(mutableListOf<ErrorBranch>())
    val errorBranches: StateFlow<List<ErrorBranch>> = _errorBranches.asStateFlow()

    private val _showErrorAlert = MutableStateFlow(false)
    val showErrorAlert: StateFlow<Boolean> = _showErrorAlert.asStateFlow()

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


    private fun validateBranches(context: Context) {
        resetErrors()
        _errorMessage.value = mutableSetOf()

        val errors = mutableListOf<ErrorBranch>()

        _branches.value.forEachIndexed { index, branch ->
            val errorBranch = ErrorBranch(id = index + 1)

            errorBranch.isInputError.value = (branch.input.value.isEmpty()
                    || !branch.input.value.isNumber()
                    || branch.input.value.toDouble() <= 0)

            errorBranch.isOutputError.value = (branch.output.value.isEmpty()
                    || !branch.output.value.isNumber()
                    || branch.output.value.toDouble() <= 0)
            if (errorBranch.isInputError.value || errorBranch.isOutputError.value) {
                _errorMessage.value.add(
                    getString(context, R.string.invalid_input) + " ${index + 1}"
                )
            }

            errorBranch.isResistorsError = branch.resistors.map { value ->
                val hasError = value.isEmpty() || !value.isNumber() || value.toDouble() <= 0
                if (hasError) _errorMessage.value.add(
                    getString(context, R.string.invalid_input) + " ${index + 1}"
                )
                hasError
            }.toMutableStateList()

            errorBranch.isEMFError = branch.emf.map { value ->
                val hasError = value.isEmpty() || !value.isNumber()
                if (hasError) _errorMessage.value.add(
                    getString(context, R.string.invalid_input) + " ${index + 1}"
                )
                hasError
            }.toMutableStateList()

            errors.add(errorBranch)


        }

        _errorBranches.value = errors
    }


    fun checkBranches(calcNavCon: NavController, context: Context) {
        validateBranches(context)

        val hasErrors = _errorBranches.value.any { branch ->
            branch.isInputError.value ||
                    branch.isOutputError.value ||
                    branch.isResistorsError.contains(true) ||
                    branch.isEMFError.contains(true)
        }
        if (hasErrors) {
            setError()
            showErrorAlert()
        }
        else {
            calculate()
            calcNavCon.navigate(route = "result")
        }
    }
    fun updateResistor(branchIndex: Int, resistorIndex: Int, newValue: String) {
        _branches.value[branchIndex].resistors[resistorIndex] = newValue

        val branch = _errorBranches.value[branchIndex]
        branch.isResistorsError[resistorIndex] =
            newValue.isEmpty() || !newValue.isNumber() || newValue.toDouble() <= 0
    }

    private fun String.isNumber(): Boolean {
        val withDot = this.replace(",", ".")
        return withDot.toDoubleOrNull() != null
    }


    private fun resetErrors() {
        _errorBranches.value = mutableListOf()
    }

    private fun calculate() {
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

    private fun resetError() {
        _isError.value = false
    }

    private fun showErrorAlert() {
        _showErrorAlert.value = true
    }

    fun hideErrorAlert() {
        _showErrorAlert.value = false
    }
}