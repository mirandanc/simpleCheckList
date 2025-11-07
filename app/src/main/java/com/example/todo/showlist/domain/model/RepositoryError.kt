package com.example.todo.showlist.domain.model

sealed class RepositoryError {
    abstract val message: String

    data class NotFound(override val message: String) : RepositoryError()
    data class DatabaseError(override val message: String) : RepositoryError()
    data class ValidationError(override val message: String) : RepositoryError()
    data class UnknownError(override val message: String) : RepositoryError()
}