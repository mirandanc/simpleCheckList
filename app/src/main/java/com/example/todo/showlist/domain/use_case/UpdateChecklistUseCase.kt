package com.example.todo.showlist.domain.use_case

import com.example.todo.showlist.domain.model.CheckList
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import javax.inject.Inject

class UpdateChecklistUseCase @Inject constructor(
    private val repository: LocalCheckListRepository
) {
    suspend operator fun invoke(checklist: CheckList) {
        repository.updateList(checklist)
    }
}