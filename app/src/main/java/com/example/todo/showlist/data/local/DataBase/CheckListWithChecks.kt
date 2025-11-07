package com.example.todo.showlist.data.local.DataBase

import androidx.room.Embedded
import androidx.room.Relation

data class CheckListWithChecks(
    @Embedded val checkList: CheckListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId"
    )
    val checks: List<CheckEntity>
)