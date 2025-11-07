package com.example.todo.showlist.data.repository

import arrow.core.Either
import arrow.core.raise.either
import com.example.todo.showlist.data.local.CheckListLocalDataSource
import com.example.todo.showlist.domain.model.Check
import com.example.todo.showlist.domain.model.CheckList
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import com.example.todo.showlist.domain.repository.RepositoryError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor (
    private val localDataSource: CheckListLocalDataSource
) : LocalCheckListRepository {

    override fun getAllLists(): Flow<Either<RepositoryError, List<CheckList>>> {
        return localDataSource.getAllLists().map { either {
            try {
                it
            } catch (e: Exception) {
                raise(RepositoryError.DatabaseError(e.message ?: "Failed to get all lists"))
            }
        } }
    }

    override suspend fun getListById(id: UUID): Either<RepositoryError, CheckList> = either {
        val list = try {
            localDataSource.getListById(id)
        } catch (e: Exception) {
            raise(RepositoryError.DatabaseError(e.message ?: "Failed to get list"))
        }

        list ?: raise(RepositoryError.NotFound("List with id $id not found"))
    }

    override suspend fun createList(title: String): Either<RepositoryError, CheckList> = either {
        if (title.isBlank()) {
            raise(RepositoryError.ValidationError("Title cannot be blank"))
        }

        try {
            val newList = CheckList(title = title)
            localDataSource.saveList(newList)
        } catch (e: Exception) {
            raise(RepositoryError.DatabaseError(e.message ?: "Failed to create list"))
        }
    }

    override suspend fun deleteList(id: UUID): Either<RepositoryError, Unit> = either {
        try {
            val deleted = localDataSource.deleteList(id)
            if (!deleted) {
                raise(RepositoryError.NotFound("List with id $id not found"))
            }
        } catch (e: Exception) {
            raise(RepositoryError.DatabaseError(e.message ?: "Failed to delete list"))
        }
    }

    override suspend fun addCheckToList(listId: UUID, check: Check): Either<RepositoryError, CheckList> = either {
        try {
            val existingList = localDataSource.getListById(listId)
                ?: raise(RepositoryError.NotFound("List with id $listId not found"))

            val updatedList = existingList.copy(
                checks = existingList.checks + check
            )
            localDataSource.updateList(updatedList)
                ?: raise(RepositoryError.DatabaseError("Failed to update list"))
        } catch (e: Exception) {
            raise(RepositoryError.DatabaseError(e.message ?: "Failed to add check to list"))
        }
    }

    override suspend fun deleteCheckFromList(listId: UUID, checkId: UUID): Either<RepositoryError, Unit> = either {
        try {
            val deleted = localDataSource.deleteCheckFromList(checkId)
            if (!deleted) {
                raise(RepositoryError.DatabaseError("Failed to delete check with id $checkId"))
            }
        } catch (e: Exception) {
            raise(RepositoryError.DatabaseError(e.message ?: "Failed to delete check from list"))
        }
    }

    override suspend fun updateList(checklist: CheckList): Either<RepositoryError, CheckList> = either {
        if (checklist.title.isBlank()) {
            raise(RepositoryError.ValidationError("Title cannot be blank"))
        }

        try {
            localDataSource.updateList(checklist)
                ?: raise(RepositoryError.DatabaseError("Failed to update list"))
        } catch (e: Exception) {
            raise(RepositoryError.DatabaseError(e.message ?: "Failed to update list"))
        }
    }

    override suspend fun updateCheck(listId: UUID, check: Check): Either<RepositoryError, Unit> = either {
        try {
            localDataSource.updateCheck(listId, check)
        } catch (e: Exception) {
            raise(RepositoryError.DatabaseError(e.message ?: "Failed to update check"))
        }
    }

}