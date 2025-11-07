package com.example.todo.showlist.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class CheckList (
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val checks: Set<Check> = emptySet(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
data class Check (
    val id: UUID = UUID.randomUUID(),
    val description: String,
    val checked: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val checkedAt: LocalDateTime? = null
)