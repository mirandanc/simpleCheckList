package com.example.todo.showlist.domain.use_case

import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import com.example.todo.showlist.domain.repository.RepositoryError
import com.example.todo.showlist.domain.model.CheckList
import javax.inject.Inject
import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import com.example.todo.showlist.domain.model.Check

class SaveCheckListUseCase @Inject constructor(
    private val repository: LocalCheckListRepository
) {
    suspend operator fun invoke(title: String, items: List<Check>): Either<RepositoryError, Unit> {
        if (title.isBlank()) {
            return RepositoryError.ValidationError("Title cannot be empty.").left()
        }
        return repository.createList(title).flatMap { newList ->
            items.forEach { check ->
                repository.addCheckToList(newList.id, check)
            }
            Either.Right(Unit)
        }
    }
}