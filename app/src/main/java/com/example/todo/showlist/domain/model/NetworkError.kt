package com.example.todo.showlist.domain.model

data class NetworkError (
    val error: ApiError,
    val t: Throwable? = null
)

enum class ApiError(val message: String){
    NetworkError("Conexão não estabelecida"),
    UnknownResponse("Resposta invalida"),
    UnknownError("Erro indetermiando")
}