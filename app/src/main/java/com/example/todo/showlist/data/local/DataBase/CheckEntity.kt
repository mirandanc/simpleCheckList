package com.example.todo.showlist.data.local.DataBase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "checks",
    foreignKeys = [
        ForeignKey(
            entity = CheckListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CheckEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val listId: UUID,
    val description: String,
    val checked: Boolean
)