package net.kep.dc_guide.ui.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.kep.dc_guide.data.BranchResultUI
import net.kep.dc_guide.data.BranchUI
import net.kep.dc_guide.data.ErrorsInBranch
import net.kep.dc_guide.model.getCurrents
import net.kep.dc_guide.model.hasBridges
import net.kep.dc_guide.model.isCircuitContinuous
import net.kep.dcc.exceptions.CircuitHasBridgesException
import net.kep.dcc.exceptions.CircuitIsNotContinuousException


class BranchViewModel: ViewModel() {

    private val _branches = MutableStateFlow(mutableListOf(BranchUI()))
    val branches: StateFlow<MutableList<BranchUI>> = _branches.asStateFlow()

    private val _result = MutableStateFlow(mutableListOf(BranchResultUI()))
    val result: StateFlow<List<BranchResultUI>> = _result.asStateFlow()

    private val _errorsInBranches = MutableStateFlow(mutableListOf<ErrorsInBranch>())
    val errorsInBranches: StateFlow<List<ErrorsInBranch>> = _errorsInBranches.asStateFlow()

    private val _showErrorAlert = MutableStateFlow(false)
    val showErrorAlert: StateFlow<Boolean> = _showErrorAlert.asStateFlow()



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

    fun validate() {
        resetErrors()

        val errorsList = mutableListOf<ErrorsInBranch>()
        var oneBranch = false
        var hasErrors = false
        var ecNotContinuous = false



        // Проверка на недостаток ветвей (до обработки всех ветвей)
        if (_branches.value.size < 2) {
            oneBranch = true

            val error = ErrorsInBranch(id = 1)
            error.messages.add("Недостаточно ветвей для замкнутой цепи. Нужно минимум 2 ветви.")
            errorsList.add(error)

            Log.d("Check 1", errorsList.toString())
        }

        if (!oneBranch) {
            _branches.value.forEachIndexed { index, branch ->
                val branchErrors = ErrorsInBranch(id = index + 1)

                var empty = false
                var hasEmpty = false

                if (checkIfBranchIsEmpty(index)) {
                    hasErrors = true
                    empty = true
                    branchErrors.messages.add("Ветвь ${index+1} пустая.")
                }

                if (!empty && checkIfParametersAreEmpty(index)) {
                    hasErrors = true
                    hasEmpty = true
                    branchErrors.messages.add("Ветвь ${index+1} содержит пустые параметры.")
                }

                if (!empty && !hasEmpty && checkForInvalidValues(index)) {
                    hasErrors = true
                    branchErrors.messages.add("Ветвь ${index+1} содержит некорректные данные.")
                }

                errorsList.add(branchErrors)
            }
        }

        if (!oneBranch && !hasErrors && checkIfCircuitIsNotContinuous()) {
            val branchErrors = ErrorsInBranch()
            ecNotContinuous = true
            branchErrors.isCircuitNotContinuous = mutableStateOf(true)
            branchErrors.messages.add("Цепь не замкнута!")

            errorsList.add(branchErrors)
        }

        if (!oneBranch && !hasErrors && !ecNotContinuous && checkIfCircuitHasBridges()) {
            val branchErrors = ErrorsInBranch()

            branchErrors.hasNoBridges = mutableStateOf(true)
            branchErrors.messages.add("Цепь имеет мосты!")

            errorsList.add(branchErrors)
        }

        _errorsInBranches.value = errorsList

        if (_showErrorAlert.value)
            Log.e(
                "BranchViewModel:validate",
                "Critical errors found during validation")
        else
            Log.i(
                "BranchViewModel:validate",
                "Validation completed without critical errors")

    }

    private fun checkIfBranchIsEmpty(branchIndex: Int): Boolean {

        // Проверяем, что индекс в пределах границ
        if (branchIndex < 0 || branchIndex >= _branches.value.size) {
            Log.e(
                "BranchViewModel:checkIfBranchIsEmpty",
                "Индекс ветви $branchIndex вне границ"
            )
            return false
        }

        val branch = _branches.value[branchIndex]

        val isInputEmpty = branch.input.value.isEmpty()
        val isOutputEmpty = branch.output.value.isEmpty()
        val areEMFsEmpty = branch.emf.all { it.isEmpty() }
        val areResistorsEmpty = branch.resistors.all { it.isEmpty() }

        Log.d(
            "BranchViewModel:checkIfBranchIsEmpty",
            "Ветвь ${branchIndex + 1}: " +
                "вход пустой = $isInputEmpty, выход пустой = $isOutputEmpty, " +
                "ЭДС пустые = $areEMFsEmpty, резисторы пустые = $areResistorsEmpty"
        )


        val errorsInBranch = _errorsInBranches.value
            .getOrNull(branchIndex) ?: ErrorsInBranch(branchIndex + 1)
        errorsInBranch.isInputError.value = isInputEmpty
        errorsInBranch.isOutputError.value = isOutputEmpty
        errorsInBranch.isEMFError.clear()
        errorsInBranch.isResistorsError.clear()

        branch.emf.forEach { emf ->
            errorsInBranch.isEMFError.add(emf.isEmpty())
        }

        branch.resistors.forEach { resistor ->
            errorsInBranch.isResistorsError.add(resistor.isEmpty())
        }

        val allElementsEmpty = isInputEmpty && isOutputEmpty && areEMFsEmpty && areResistorsEmpty
        Log.d(
            "BranchViewModel:checkIfBranchIsEmpty",
            "Все элементы ветви ${branchIndex + 1} пустые: $allElementsEmpty"
        )

        return allElementsEmpty
    }

    private fun checkIfParametersAreEmpty(branchIndex: Int): Boolean {
        // Защита от выхода за границы
        if (branchIndex < 0 || branchIndex >= _branches.value.size) {
            Log.e(
                "BranchViewModel:checkIfParametersAreEmpty",
                "Индекс ветви $branchIndex вне границ"
            )
            return false
        }

        val branch = _branches.value[branchIndex]

        val isInputEmpty = branch.input.value.isEmpty()
        val isOutputEmpty = branch.output.value.isEmpty()
        val areEMFsEmpty = branch.emf.map { it.isEmpty() }
        val areResistorsEmpty = branch.resistors.map { it.isEmpty() }

        Log.d(
            "BranchViewModel:checkIfParametersAreEmpty",
            "Ветвь ${branchIndex + 1}: " +
                "вход пустой = $isInputEmpty, выход пустой = $isOutputEmpty, " +
                "ЭДС пустые = $areEMFsEmpty, резисторы пустые = $areResistorsEmpty"
        )

        val errorsInBranch = _errorsInBranches.value.getOrNull(branchIndex) ?: ErrorsInBranch(branchIndex + 1)

        errorsInBranch.isInputError.value = isInputEmpty
        errorsInBranch.isOutputError.value = isOutputEmpty

        errorsInBranch.isEMFError.clear()
        errorsInBranch.isEMFError.addAll(areEMFsEmpty)

        errorsInBranch.isResistorsError.clear()
        errorsInBranch.isResistorsError.addAll(areResistorsEmpty)

        val hasEmptyParameters = isInputEmpty || isOutputEmpty ||
                areEMFsEmpty.contains(true) ||
                areResistorsEmpty.contains(true)

        Log.d(
            "BranchViewModel:checkIfParametersAreEmpty",
            "Ветвь ${branchIndex + 1} имеет пустые параметры: $hasEmptyParameters"
        )

        return hasEmptyParameters
    }

    private fun checkForInvalidValues(branchIndex: Int): Boolean {
        // Защита от выхода за границы
        if (branchIndex < 0 || branchIndex >= _branches.value.size) {
            Log.e(
                "BranchViewModel:checkForInvalidValues",
                "Индекс ветви $branchIndex вне границ"
            )
            return false
        }

        val branch = _branches.value[branchIndex]
        val errorsInBranch = _errorsInBranches.value.getOrNull(branchIndex) ?: ErrorsInBranch(branchIndex + 1)

        var hasInvalidValue = false

        // Вход
        errorsInBranch.isInputError.value = branch.input.value.isEmpty() ||
                !branch.input.value.isNumber() ||
                !branch.input.value.isPositive()
        if (errorsInBranch.isInputError.value) {
            hasInvalidValue = true
            Log.d(
                "BranchViewModel:checkForInvalidValues",
                "Ветвь ${branchIndex + 1}: некорректное значение входа " +
                        branch.input.value
            )
        }

        // Выход
        errorsInBranch.isOutputError.value = branch.output.value.isEmpty() ||
                !branch.output.value.isNumber() ||
                !branch.output.value.isPositive()
        if (errorsInBranch.isOutputError.value) {
            hasInvalidValue = true
            Log.d(
                "BranchViewModel:checkForInvalidValues",
                "Ветвь ${branchIndex + 1}: некорректное значение выхода " +
                        branch.output.value
            )
        }

        // ЭДС
        while (errorsInBranch.isEMFError.size < branch.emf.size) {
            errorsInBranch.isEMFError.add(false) // Инициализируем ошибки для новых источников ЭДС
        }
        branch.emf.forEachIndexed { emfIndex, emf ->
            // Проверка ЭДС на ошибки
            errorsInBranch.isEMFError[emfIndex] = emf.isEmpty() || !emf.isNumber()
            if (errorsInBranch.isEMFError[emfIndex]) {
                hasInvalidValue = true
                Log.d(
                    "BranchViewModel:checkForInvalidValues",
                    "Ветвь ${branchIndex + 1}: " +
                            "некорректное значение ЭДС на индексе $emfIndex: $emf"
                )
            }
        }

        // Резисторы
        while (errorsInBranch.isResistorsError.size < branch.resistors.size) {
            // Инициализируем ошибки для новых резисторов
            errorsInBranch.isResistorsError.add(false)
        }
        branch.resistors.forEachIndexed { resistorIndex, resistor ->
            // Проверка резисторов на ошибки
            errorsInBranch.isResistorsError[resistorIndex] = resistor.isEmpty() ||
                    !resistor.isNumber() ||
                    !resistor.isPositive()

            if (errorsInBranch.isResistorsError[resistorIndex]) {
                hasInvalidValue = true
                Log.d(
                    "BranchViewModel:checkForInvalidValues",
                    "Ветвь ${branchIndex + 1}: некорректное значение резистора на индексе " +
                            "$resistorIndex: '${resistor}'.")
            }
        }

        // Обновляем ошибки для этой ветви в списке ошибок
        if (hasInvalidValue) {
            Log.e("BranchViewModel:checkForInvalidValues", _errorsInBranches.value.toString())

            // Инициализация, чтобы избежать ошибки
            if (_errorsInBranches.value.size <= branchIndex) {

                // Инициализируем список с размером branchIndex + 1, если размер меньше
                _errorsInBranches.value = _errorsInBranches.value.toMutableList().apply {
                    while (size <= branchIndex) {
                        add(ErrorsInBranch())
                    }
                }
            }
            _errorsInBranches.value[branchIndex] = errorsInBranch
        }

        return hasInvalidValue
    }

    private fun checkIfCircuitIsNotContinuous(): Boolean {
        var circuitNotContinuous = false
        try {
            isCircuitContinuous(_branches.value)
        } catch (e: CircuitIsNotContinuousException) {
            circuitNotContinuous = true
            Log.e("BranchViewModel:checkIfCircuitIsNotContinuous", circuitNotContinuous.toString())
        }

        return circuitNotContinuous
    }


    private fun checkIfCircuitHasBridges(): Boolean {
        var ecHasBridges = false

        try {
            ecHasBridges = hasBridges(_branches.value)
        } catch (e: CircuitHasBridgesException) {
            Log.e("BranchViewModel:checkIfCircuitHasBridges", ecHasBridges.toString())
        }

        return ecHasBridges
    }


    private fun String.isPositive(): Boolean {
        return (this.toIntOrNull() ?: -1) > 0
    }


    private fun String.isNumber(): Boolean {
        val withDot = this.replace(",", ".")
        return withDot.toDoubleOrNull() != null
    }


    private fun resetErrors() {
        _errorsInBranches.value = mutableListOf()
    }


    fun makeResult(calcNavCon: NavController) {
        validate()
        Log.e("ERROR", "hasError: ${hasErrors()}")
        if (hasErrors()) {
            showErrorAlert()
        } else {
            calculate()
            calcNavCon.navigate(route = "result")
        }
    }


    private fun hasErrors(): Boolean {
        return _errorsInBranches.value.any { errorsInBranch ->
            errorsInBranch.isInputError.value ||
                    errorsInBranch.isOutputError.value ||
                    errorsInBranch.isEMFError.any { it } ||
                    errorsInBranch.isResistorsError.any { it } ||
                    errorsInBranch.isCircuitNotContinuous.value ||
                    errorsInBranch.messages.isNotEmpty()
        }
    }


    private fun calculate() {
        _branches.value.forEachIndexed { index, branch ->
            Log.d(
                "BranchViewModel:calculate",
                "Branch ${index + 1}:" +
                    "Node 1 = ${branch.input.value},\n" +
                    "Node 2 = ${branch.output.value},\n" +
                    "EMF = ${branch.emf},\n" +
                    "Resistance = ${branch.resistors}."
            )
        }

        try {
            val res = getCurrents(_branches.value)

            val results = res.mapIndexed { index, current ->
                BranchResultUI(
                    id = index+1,
                    current = current,
                    inputNode = _branches.value[index].input.value.toInt(),
                    outputNode = _branches.value[index].output.value.toInt(),
                    summarizedEMF = _branches.value[index].totalEMF(),
                    summarizedResistance = _branches.value[index].totalResistance()
                )
            }.toMutableList()
            _result.value = results
        } catch (e: Exception) {
            println("Error: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }


    private fun showErrorAlert() {
        _showErrorAlert.value = true
    }

    fun hideErrorAlert() {
        _showErrorAlert.value = false
    }


//    fun onValueChange(branchIndex: Int, field: Field, newText: String, indexInList: Int = -1) {
//        val branch = _branches.value[branchIndex]
//
//        when (field) {
//            Field.INPUT -> branch.input.value = newText
//            Field.OUTPUT -> branch.output.value = newText
//            Field.EMF -> if (indexInList in branch.emf.indices) branch.emf[indexInList] = newText
//            Field.RESISTOR -> if (indexInList in branch.resistors.indices) branch.resistors[indexInList] = newText
//        }
//
//        validate() // Перепроверка всех ошибок после изменения значения
//    }
//

}