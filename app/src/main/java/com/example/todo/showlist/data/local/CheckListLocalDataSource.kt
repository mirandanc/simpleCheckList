package com.example.todo.showlist.data.local

import com.example.todo.showlist.domain.model.Check
import com.example.todo.showlist.domain.model.CheckList
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CheckListLocalDataSource {
    fun getAllLists(): Flow<List<CheckList>>
    suspend fun getListById(id: UUID): CheckList?
    suspend fun saveList(list: CheckList): CheckList
    suspend fun deleteList(id: UUID): Boolean
    suspend fun updateList(list: CheckList): CheckList?
    suspend fun updateCheck(listId: UUID, check: Check)

    suspend fun deleteCheckFromList(checkId: UUID): Boolean

}