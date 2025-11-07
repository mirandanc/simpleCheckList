package com.example.todo.showlist.domain.use_case

import arrow.core.Either
import com.example.todo.showlist.domain.model.CheckList
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import com.example.todo.showlist.domain.repository.RepositoryError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCheckListsUseCase @Inject constructor(
    private val repository: LocalCheckListRepository
) {
    operator fun invoke(): Flow<Either<RepositoryError, List<CheckList>>> {
        return repository.getAllLists()
    }
}