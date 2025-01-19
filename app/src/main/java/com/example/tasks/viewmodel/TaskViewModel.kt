package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.model.CompletedDataClass
import com.example.tasks.model.TaskDataClass
import com.example.tasks.model.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val allTasks:StateFlow<List<TaskDataClass>> = repository.allTask.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList() )

    val allCompletedTask:StateFlow<List<CompletedDataClass>> = repository.allCompleted.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList() )

    fun addTask(task: TaskDataClass){
        viewModelScope.launch {
            repository.insertTask(task)

        }
    }

    fun deleteTask(task:TaskDataClass){
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun addCompleted(task:CompletedDataClass){
        viewModelScope.launch {
            repository.insertCompleted(task)

        }


    }

    fun deleteCompleted(completed: CompletedDataClass){
        viewModelScope.launch {
            repository.deleteCompleted(completed)
        }

    }
}