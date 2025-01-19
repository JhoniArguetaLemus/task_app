package com.example.tasks.model

import androidx.core.app.TaskStackBuilder
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao{
    @Query("SELECT * FROM task_table")
     fun  getAllTasks():Flow<List<TaskDataClass>>

    @Delete
    suspend fun deleteTask(task:TaskDataClass)

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insertTask(task:TaskDataClass)

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun  insertCompleted(completed:CompletedDataClass)



    @Query("""
        SELECT * FROM task_table
        inner  join completed
        on task_table.id=completed.id
        
    """)
    fun getAllCompleted():Flow<List<CompletedDataClass>>

    @Delete
    suspend fun deleteCompletedTask(task:CompletedDataClass)



}