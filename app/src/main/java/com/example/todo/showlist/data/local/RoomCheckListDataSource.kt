package com.example.todo.showlist.data.local

import com.example.todo.showlist.data.local.DataBase.CheckListDao
import com.example.todo.showlist.domain.model.Check
import com.example.todo.showlist.domain.model.CheckList
import com.example.todo.showlist.data.mapper.toDomain
import com.example.todo.showlist.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class RoomCheckListDataSource @Inject constructor(
    private val dao: CheckListDao
) : CheckListLocalDataSource {

    override fun getAllLists(): Flow<List<CheckList>> {
        return dao.getAllListsWithChecks().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getListById(id: UUID): CheckList? {
        return dao.getListWithChecksById(id)?.toDomain()
    }

    override suspend fun saveList(list: CheckList): CheckList {
        val (checkListEntity, checkEntities) = list.toEntity()
        dao.insertCheckList(checkListEntity)
        if (checkEntities.isNotEmpty()) {
            dao.insertChecks(checkEntities)
        }
        return list
    }

    override suspend fun deleteList(id: UUID): Boolean {
        dao.deleteCheckListById(id)
        return true
    }

    override suspend fun deleteCheckFromList(checkId: UUID): Boolean {
        dao.deleteCheckById(checkId)
        return true
    }


    override suspend fun updateList(list: CheckList): CheckList? {
        saveList(list)
        return list
    }

    override suspend fun updateCheck(listId: UUID, check: Check) {
        dao.updateCheck(check.toEntity(listId))
    }
}