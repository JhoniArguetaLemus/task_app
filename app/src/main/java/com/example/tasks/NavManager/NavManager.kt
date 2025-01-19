package com.example.tasks.NavManager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasks.views.ScaffoldView
import com.example.tasks.views.ShowTasks

@Composable
fun NavManager(){
    val navController= rememberNavController()

    NavHost(navController=navController, startDestination = "HomeView"){
        composable("HomeView"){
            ScaffoldView(navController=navController)
        }

        composable("ShowTasks"){

        }
    }
}