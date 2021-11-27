package com.todolist.base

import android.app.Application
import androidx.room.Room
import com.todolist.db.AppDatabase
import com.todolist.featuer.home.ViewModelHome
import com.todolist.repository.TaskRepository
import com.todolist.repository.TaskRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    companion object{
        const val EDIT_DIALOG="dialog"
        const val EDIT_TASK="editTask"
    }
    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single { AppDatabase.getInstance(androidApplication()) }
            factory <TaskRepository> {TaskRepositoryImpl(get())}
            viewModel { ViewModelHome(get()) }
        }
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}