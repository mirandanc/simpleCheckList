package com.example.todo.showlist.data.local.DataBase

import androidx.room.PrimaryKey
import androidx.room.Entity
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "checklists")
data class CheckListEntity(
    @PrimaryKey val id: UUID,
    val title: String,
    val createdAt: LocalDateTime
)