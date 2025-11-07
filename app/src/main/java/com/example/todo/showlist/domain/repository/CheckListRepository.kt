package com.example.todo.showlist.domain.repository

import arrow.core.Either
import com.example.todo.showlist.domain.model.CheckList
import com.example.todo.showlist.domain.model.NetworkError

interface CheckListRepository {
    suspend fun getLists(): Either<NetworkError, List<CheckList>>
}