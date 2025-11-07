package com.example.todo.showlist.presentation.lists_screen

import com.example.todo.showlist.domain.use_case.CheckListUseCases
import com.example.todo.showlist.domain.model.CheckList
import com.example.todo.showlist.domain.model.Check
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val useCases: CheckListUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(CheckListViewState())
    val state = _state.asStateFlow()
    private var getChecklistsJob: Job? = null

    init {
        loadChecklists()
    }

    fun loadChecklists() {
        getChecklistsJob?.cancel()
        getChecklistsJob = useCases.getCheckLists()
            .onEach { result ->
                result.onRight { checkLists ->
                    _state.update {
                        it.copy(
                            checkLists = checkLists,
                            isLoading = false
                        )
                    }
                }.onLeft { error ->
                    println(error)
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
            }.launchIn(viewModelScope)
    }
//OPTIMISTIC UPLOAD
    fun onCheckChanged(checkList: CheckList, check: Check) {
        viewModelScope.launch {
            useCases.updateCheckState(checkList.id, check)
        }
    }

    fun deleteList(listId: UUID) {
        viewModelScope.launch {
            useCases.deleteCheckList(listId)
        }
    }
}