package com.todolist.featuer.home

import androidx.lifecycle.*
import com.todolist.base.Resource
import com.todolist.data.Task
import com.todolist.repository.TaskRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewModelHome(private val taskRepository: TaskRepository) : ViewModel() {

    private val errorText = "درخواست با خطا مواجه شد دوباره امتحان کنید"
    private val successText = "درخواست با موفقیت انجام شد!"
    private val _searchTask = MutableLiveData<List<Task>>()
    val searchTask: LiveData<List<Task>> = _searchTask

    private val _operationsGetTask = MutableLiveData<Resource<List<Task>>>()
    val operationsGetTask: LiveData<Resource<List<Task>>> = _operationsGetTask

    private val _operationsDeleteAllTask = MutableLiveData<Resource<String>>()
    val operationsDeleteAllTask: LiveData<Resource<String>> = _operationsDeleteAllTask


    init {
        getTask()
    }

    //get task
    private fun getTask() {
        viewModelScope.launch {
            _operationsGetTask.value = Resource.Loading()
            taskRepository.getTask().catch {
                _operationsGetTask.value = Resource.Error(errorText)
            }.collect {
                _operationsGetTask.value = Resource.Success(it)
            }
        }
    }


    // delete all task
    fun deleteAllTasks(): LiveData<Resource<String>> = liveData {
        emit(Resource.Loading())
        val data = runCatching {
            taskRepository.deleteAllTask()
        }
        data.onFailure {
            emit(Resource.Error(errorText))
        }.onSuccess {
            emit(Resource.Success(successText))
        }
    }

    fun addTask(task: Task): LiveData<Resource<String>> = liveData {
        emit(Resource.Loading())
        val data = runCatching {
            taskRepository.addTask(task)
        }
        data.onSuccess {
            emit(Resource.Success(successText))
        }.onFailure {
            emit(Resource.Error(errorText))
        }

    }

    // update special task
    fun update(task: Task): LiveData<Resource<String>> = liveData {
        emit(Resource.Loading())
        runCatching {
            taskRepository.updateTask(task)
        }.onSuccess {
            emit(Resource.Success(successText))
        }.onFailure {
            emit(Resource.Error(errorText))
        }
    }

    // delete special task
    fun deleteTask(task: Task): LiveData<Resource<String>> = liveData {
        emit(Resource.Loading())
        runCatching {
            taskRepository.deleteTask(task)
        }.onSuccess {
            emit(Resource.Success(successText))
        }.onFailure {
            emit(Resource.Error(errorText))
        }

    }

    //search task name by view model lifecycle
    fun searchTaskName(taskName: String): LiveData<Resource<List<Task>>> = liveData {
        emit(Resource.Loading())
        runCatching {
            taskRepository.searchName(taskName)
        }.onSuccess {
            emit(Resource.Success(it))
        }.onFailure {
            emit(Resource.Error(errorText))
        }
    }


}
