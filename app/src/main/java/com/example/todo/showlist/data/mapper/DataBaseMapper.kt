package com.example.todo.showlist.data.mapper

import com.example.todo.showlist.data.local.DataBase.CheckEntity
import com.example.todo.showlist.data.local.DataBase.CheckListEntity
import com.example.todo.showlist.data.local.DataBase.CheckListWithChecks
import com.example.todo.showlist.domain.model.Check
import com.example.todo.showlist.domain.model.CheckList
import java.util.UUID

fun CheckListWithChecks.toDomain(): CheckList {
    return CheckList(
        id = this.checkList.id,
        title = this.checkList.title,
        checks = this.checks.map { it.toDomain() }.toSet()
    )
}

fun CheckEntity.toDomain(): Check {
    return Check(
        id = this.id,
        description = this.description,
        checked = this.checked
    )
}

fun CheckList.toEntity(): Pair<CheckListEntity, List<CheckEntity>> {
    val checkListEntity = CheckListEntity(
        id = this.id,
        title = this.title,
        createdAt = this.createdAt
    )
    val checkEntities = this.checks.map { it.toEntity(this.id) }
    return Pair(checkListEntity, checkEntities)
}

fun Check.toEntity(listId: UUID): CheckEntity {
    return CheckEntity(
        id = this.id,
        listId = listId,
        description = this.description,
        checked = this.checked
    )
}