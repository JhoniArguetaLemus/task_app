package com.example.tasks.model

import kotlinx.coroutines.flow.Flow

class TaskRepository(private  val taskDao: TaskDao) {
    val allTask:Flow<List<TaskDataClass>>  = taskDao.getAllTasks()

    val allCompleted:Flow<List<CompletedDataClass>> = taskDao.getAllCompleted()


    suspend fun insertTask(task: TaskDataClass){
        taskDao.insertTask(task)
    }

    suspend fun deleteTask (task:TaskDataClass){
        taskDao.deleteTask(task)
    }


    suspend fun  insertCompleted(completed:CompletedDataClass){
        taskDao.insertCompleted(completed)
    }

    suspend fun deleteCompleted(completed: CompletedDataClass){
        taskDao.deleteCompletedTask(completed)

    }


}