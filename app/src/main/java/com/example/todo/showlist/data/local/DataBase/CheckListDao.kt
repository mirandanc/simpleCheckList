package com.example.todo.showlist.data.local.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CheckListDao {
    @Transaction
    @Query("SELECT * FROM checklists ORDER BY createdAt DESC")
    fun getAllListsWithChecks(): Flow<List<CheckListWithChecks>>

    @Transaction
    @Query("SELECT * FROM checklists WHERE id = :id")
    suspend fun getListWithChecksById(id: UUID): CheckListWithChecks?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckList(checkList: CheckListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecks(checks: List<CheckEntity>)

    @Update
    suspend fun updateCheck(check: CheckEntity)

    @Query("DELETE FROM checklists WHERE id = :id")
    suspend fun deleteCheckListById(id: UUID)

    @Query("DELETE FROM checks WHERE id = :checkId")
    suspend fun deleteCheckById(checkId: UUID)
}