package com.example.todo.showlist.presentation.add_new_list_screen

import com.example.todo.showlist.presentation.add_checklist.ChecklistItem
import com.example.todo.showlist.domain.use_case.SaveCheckListUseCase
import com.example.todo.showlist.domain.model.Check
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddChecklistViewModel @Inject constructor(
    private val saveCheckListUseCase: SaveCheckListUseCase
) : ViewModel() {
    private val _onSaveSuccess = MutableSharedFlow<Boolean>()
    val onSaveSuccess = _onSaveSuccess.asSharedFlow()

    fun saveChecklist(title: String, items: List<ChecklistItem>) {
        viewModelScope.launch {
            val checksToSave = items
                .filter { it.text.isNotBlank() }
                .map { Check(description = it.text) }

            saveCheckListUseCase(title, checksToSave).onRight {
                _onSaveSuccess.emit(true)
            }
        }
    }
}