package com.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.todolist.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM Task" )
    fun getTask():Flow<List<Task>>

    @Delete()
    suspend fun deleteTask(task: Task)

    @Update()
    suspend fun update(task: Task)

    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM Task WHERE name LIKE '%' || :taskName || '%'")
    suspend fun search(taskName:String):List<Task>

}