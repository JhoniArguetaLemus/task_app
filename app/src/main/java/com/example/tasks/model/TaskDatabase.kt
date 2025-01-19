package com.example.tasks.model

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskDataClass::class, CompletedDataClass::class], version = 4, exportSchema = false)
 abstract  class TaskDatabase:RoomDatabase() {
     abstract  fun taskDao():TaskDao

     companion object{
         @Volatile
         var INSTANCE:TaskDatabase?=null

         fun getDatabase(context: Context):TaskDatabase{
             return  INSTANCE ?: synchronized(this){
                 val instance= Room.databaseBuilder(
                     context.applicationContext,
                     TaskDatabase::class.java,
                     "task_database",

                 ).fallbackToDestructiveMigration()
                     .build()
                 INSTANCE=instance
                 instance
             }
         }



     }



}