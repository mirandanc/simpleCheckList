package com.example.todo.showlist.domain.use_case

import arrow.core.Either
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import com.example.todo.showlist.domain.repository.RepositoryError
import java.util.UUID
import javax.inject.Inject

class DeleteCheckListUseCase @Inject constructor(
    private val repository: LocalCheckListRepository
) {
    suspend operator fun invoke(listId: UUID): Either<RepositoryError, Unit> {
        return repository.deleteList(listId)
    }
}