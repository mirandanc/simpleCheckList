package com.example.todo.showlist.domain.use_case

import arrow.core.Either
import com.example.todo.showlist.domain.model.CheckList
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import com.example.todo.showlist.domain.repository.RepositoryError
import java.util.UUID
import javax.inject.Inject

class GetCheckListByIdUseCase @Inject constructor(
    private val repository: LocalCheckListRepository
) {
    suspend operator fun invoke(id: UUID): Either<RepositoryError, CheckList> {
        return repository.getListById(id)
    }
}