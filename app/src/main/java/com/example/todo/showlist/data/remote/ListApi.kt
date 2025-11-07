package com.example.todo.showlist.data.remote

import com.example.todo.showlist.domain.model.CheckList
import retrofit2.http.GET

interface ListApi {
    @GET("lists")
    suspend fun getLists(): List<CheckList>
}