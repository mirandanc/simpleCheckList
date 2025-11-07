package com.example.todo.showlist.presentation.lists_screen

import com.example.todo.showlist.domain.model.CheckList

data class CheckListViewState (
    val isLoading: Boolean = false,
    val checkLists: List<CheckList> = emptyList(),
    val error: String? = null
)