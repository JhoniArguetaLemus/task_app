package com.example.tasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskDataClass(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val title: String,
    val description: String
)


@Entity(tableName = "completed")
data class CompletedDataClass(
   @PrimaryKey(autoGenerate = true) val id: Int=0,
    val title: String,
    val description: String
)
