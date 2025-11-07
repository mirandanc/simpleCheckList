package com.example.todo.showlist.presentation.util.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.utils.EventBus
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event: Any) {
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}