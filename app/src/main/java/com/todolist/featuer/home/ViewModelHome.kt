package com.todolist.featuer.home

import androidx.lifecycle.*
import com.todolist.data.Task
import com.todolist.repository.TaskRepository
import kotlinx.coroutines.launch

class ViewModelHome(val taskRepository: TaskRepository) : ViewModel() {
    private val _searchTask = MutableLiveData<List<Task>>()
    val searchTask: LiveData<List<Task>> = _searchTask

    // get all task by live data lifecycle
    val getTask: LiveData<List<Task>> = liveData {
        val tasks = taskRepository.getTask().asLiveData()
        emitSource(tasks)
    }

    // delete all task
    fun deleteAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTask()
        }
    }

    //add one task
    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.addTask(task)
        }
    }

    // update special task
    fun update(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    // delete special task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    //search task name by view model lifecycle
    fun searchTaskName(taskName: String) {
        viewModelScope.launch {
            _searchTask.value = taskRepository.searchName(taskName)
        }
    }


}
