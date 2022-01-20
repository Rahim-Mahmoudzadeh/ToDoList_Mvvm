package com.todolist.data

data class ResourceData(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isUserLoggedIn: List<Task> = emptyList()
)
