package com.example.todo.showlist.domain.repository

import arrow.core.Either
import com.example.todo.showlist.domain.model.Check
import com.example.todo.showlist.domain.model.CheckList
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalCheckListRepository {
    fun getAllLists(): Flow<Either<RepositoryError, List<CheckList>>>
    suspend fun getListById(id: UUID): Either<RepositoryError, CheckList>
    suspend fun createList(title: String): Either<RepositoryError, CheckList>
    suspend fun deleteList(id: UUID): Either<RepositoryError, Unit>
    suspend fun addCheckToList(listId: UUID, check: Check): Either<RepositoryError, CheckList>
    suspend fun deleteCheckFromList(listId: UUID, checkId: UUID): Either<RepositoryError, Unit>
    suspend fun updateCheck(listId: UUID, check: Check): Either<RepositoryError, Unit>
    suspend fun updateList(checklist: CheckList): Either<RepositoryError, CheckList>
}

sealed class RepositoryError {
    data class NotFound(val message: String) : RepositoryError()
    data class DatabaseError(val message: String) : RepositoryError()
    data class ValidationError(val message: String) : RepositoryError()
    data class UnknownError(val message: String) : RepositoryError()
}