package com.example.tasks.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import com.example.tasks.R
import com.example.tasks.viewmodel.TaskViewModel
import com.example.tasks.model.TaskDataClass
import com.example.tasks.model.TaskDatabase
import com.example.tasks.model.TaskRepository
import com.example.tasks.ui.theme.Yellow
import com.example.tasks.viewmodel.TaskViewModelFactory
import kotlinx.coroutines.launch



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScaffoldView (navController: NavController){

    val context= LocalContext.current

    val database = TaskDatabase.getDatabase(navController.context)
    val repository = TaskRepository(database.taskDao())

    val viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repository))

    var drawerState = rememberDrawerState(DrawerValue.Closed)
    var scope= rememberCoroutineScope()
    var showAlertDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var selectedItem by remember { mutableStateOf("Tareas") }

    val list= listOf(
        "Tareas" to R.drawable.check_outline,
        "Completadas" to R.drawable.check_outline
    )


    if(showAlertDialog){
        AlertDialog(
            title = { Text("Add Note") },
            text = {

                Column {


                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("Title") },
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Description") },
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = TextFieldDefaults.colors(
                          focusedIndicatorColor = Color.Black
                        )
                    )

                }




            },
            onDismissRequest = { showAlertDialog=false },
            dismissButton = {
                Button(onClick = {showAlertDialog=false},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red)
                    ){
                    Text("Cancel", fontSize = 20.sp)
                }
            },
            confirmButton = {
                Button(onClick = {
                    if(title.isNotEmpty() && description.isNotEmpty()){
                        viewModel.addTask(TaskDataClass(title=title, description=description))
                        title=""
                        description=""
                        showAlertDialog=false
                    }else{
                        Toast.makeText(context, "Debes rellenar todos los campos!!", Toast.LENGTH_LONG).show()
                    }



                },
                    colors=ButtonDefaults.buttonColors(
                        containerColor = Yellow
                    )) {
                    Text("Add", color = Color.Black, fontSize = 20.sp)
                }
            }

        )
    }

    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Text("Tasks", fontSize = 24.sp)
                HorizontalDivider()
                list.forEach{(title, icon)->
                    Spacer(Modifier.height(12.dp))
                    NavigationDrawerItem(
                        icon={
                            Icon(painter = painterResource(icon), contentDescription = title)
                        },
                        label = { Text(title, fontSize = 20.sp) },
                        selected = title==selectedItem,
                        onClick = {

                            selectedItem=title
                            scope.launch {
                                drawerState.close()
                            }

                        }
                    )

                }


            }

        }
    ) {

        Scaffold(
            topBar = {
                TopBar(

                    title = selectedItem,
                    onDrawerOpen = {
                        scope.launch{
                           drawerState.open()
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {showAlertDialog=true},
                    containerColor = Yellow,
                    ) {
                    Icon(Icons.Default.Add, contentDescription = "Menu")
                }
            }
        ) {padding->

            when(selectedItem){
                "Tareas" -> ShowTasks(Modifier.padding(padding), viewModel)
                "Completadas" -> ShowCompletedTask(Modifier.padding(padding), viewModel)

            }



        }




    }

}









@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onDrawerOpen: () -> Unit, title:String){
    CenterAlignedTopAppBar(
        title = { Text(title)},
        navigationIcon = {
            Icon(Icons.Default.Menu, contentDescription = "Menu",
                modifier= Modifier.padding(start = 10.dp).size(30.dp)
                    .clickable { onDrawerOpen() }
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Yellow
        )
    
    )
}