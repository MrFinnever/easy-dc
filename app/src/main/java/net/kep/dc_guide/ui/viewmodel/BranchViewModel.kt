package net.kep.dc_guide.ui.viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.kep.dc_guide.data.Branch


class BranchViewModel: ViewModel() {
    private val _branches = MutableStateFlow(mutableListOf(Branch()))
    val branches: StateFlow<MutableList<Branch>> = _branches.asStateFlow()

    fun addBranch() {
        val newBranch = Branch(id = _branches.value.size + 1)
        _branches.value = _branches.value.toMutableList().apply { add(newBranch) }
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
}