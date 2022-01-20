package com.todolist.repository

import androidx.lifecycle.LiveData
import com.todolist.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getTask():Flow<List<Task>>

    suspend fun addTask(task: Task)

    suspend fun deleteAllTask()

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun searchName(nameTask:String):List<Task>
}