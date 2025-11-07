package com.example.todo.showlist.presentation.edit_list_screen


import com.example.todo.showlist.domain.use_case.GetCheckListByIdUseCase
import com.example.todo.showlist.domain.use_case.UpdateChecklistUseCase
import com.example.todo.showlist.domain.model.Check
import androidx.compose.runtime.mutableStateListOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.asSharedFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.UUID

data class EditableChecklistItem(var check: Check)

@HiltViewModel
class EditCheckListViewModel @Inject constructor(
    private val getCheckListByIdUseCase: GetCheckListByIdUseCase,
    private val updateChecklistUseCase: UpdateChecklistUseCase
) : ViewModel() {
    val title = mutableStateOf("")
    val checklistItems = mutableStateListOf<EditableChecklistItem>()
    private var originalChecklistId: UUID? = null

    private val _navigateUp = MutableSharedFlow<Unit>()
    val navigateUp = _navigateUp.asSharedFlow()

    fun loadChecklist(listId: UUID) {
        if (originalChecklistId == listId) return

        viewModelScope.launch {
            getCheckListByIdUseCase(listId).onRight { checklist ->
                originalChecklistId = checklist.id
                title.value = checklist.title
                checklistItems.addAll(checklist.checks.map { EditableChecklistItem(it.copy()) })
            }
        }
    }

    fun updateItem(index: Int, newDescription: String? = null, newCheckedState: Boolean? = null ) {
        if (index in checklistItems.indices) {
            val currentItem = checklistItems[index]
            val updatedCheck = currentItem.check.copy(
                description = newDescription ?: currentItem.check.description,
                checked = newCheckedState ?: currentItem.check.checked
            )
            checklistItems[index] = currentItem.copy(check = updatedCheck)
        }
    }

    fun addNewItem() {
        val newItem = Check(description = "", checked = false)
        checklistItems.add(EditableChecklistItem(newItem))
    }

    fun saveChanges() {
        viewModelScope.launch {
            originalChecklistId?.let { listId ->
                getCheckListByIdUseCase(listId).onRight { originalList ->
                    val updatedChecks = checklistItems
                        .filter { it.check.description.isNotBlank() }
                        .map { it.check }
                        .toSet()

                    val updatedList = originalList.copy(
                        title = title.value,
                        checks = updatedChecks
                    )

                    updateChecklistUseCase(updatedList)
                    _navigateUp.emit(Unit)
                }
            }
        }
    }

    fun moveItem(from: Int, to: Int) {
        if (from in checklistItems.indices && to in checklistItems.indices) {
            val fromItem = checklistItems.removeAt(from)
            checklistItems.add(to, fromItem)
        }
    }

    fun deleteItem(index: Int) {
        checklistItems.removeAt(index)
    }
}