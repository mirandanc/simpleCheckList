package com.example.todo.showlist.domain.use_case

import arrow.core.Either
import com.example.todo.showlist.domain.model.Check
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import com.example.todo.showlist.domain.repository.RepositoryError
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class UpdateCheckStateUseCase @Inject constructor(
    private val repository: LocalCheckListRepository
) {
    suspend operator fun invoke(listId: UUID, check: Check): Either<RepositoryError, Unit> {

        val updatedCheck = check.copy(
            checked = !check.checked,
            checkedAt = if (!check.checked) LocalDateTime.now() else null
        )

        return repository.updateCheck(listId, updatedCheck)
    }
}