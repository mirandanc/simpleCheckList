package com.example.todo.showlist.domain.use_case

import arrow.core.Either
import com.example.todo.showlist.domain.model.Check
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import com.example.todo.showlist.domain.repository.RepositoryError
import java.util.UUID
import javax.inject.Inject

class ListScreenUseCase @Inject constructor(
    private val repository: LocalCheckListRepository
) {
    suspend operator fun invoke(listId: UUID, check: Check): Either<RepositoryError, Unit> {
        return repository.updateCheck(listId, check)
    }
}