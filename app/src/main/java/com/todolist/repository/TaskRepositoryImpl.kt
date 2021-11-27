package com.todolist.repository

import androidx.lifecycle.LiveData
import com.todolist.db.AppDatabase
import com.todolist.data.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val appDatabase: AppDatabase) : TaskRepository {

    override fun getTask(): Flow<List<Task>> = appDatabase.taskDao().getTask()

    override suspend fun addTask(task: Task) = appDatabase.taskDao().addTask(task)

    override suspend fun deleteAllTask() = appDatabase.taskDao().deleteAllTasks()

    override suspend fun deleteTask(task: Task) = appDatabase.taskDao().deleteTask(task)

    override suspend fun updateTask(task: Task) = appDatabase.taskDao().update(task)

    override suspend fun searchName(nameTask: String) : List<Task> = appDatabase.taskDao().search(nameTask)

}