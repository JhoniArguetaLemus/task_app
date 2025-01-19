package com.example.tasks.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.R
import com.example.tasks.model.CompletedDataClass
import com.example.tasks.model.TaskDataClass
import com.example.tasks.model.TaskDatabase
import com.example.tasks.model.TaskRepository
import com.example.tasks.viewmodel.TaskViewModel
import com.example.tasks.viewmodel.TaskViewModelFactory

@Composable
fun ShowCompletedTask(modifier: Modifier = Modifier, viewModel: TaskViewModel){
    val context= LocalContext.current
    val database = TaskDatabase.getDatabase(context)
    val repository = TaskRepository(database.taskDao())

    val viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repository))
    val tasks by viewModel.allCompletedTask.collectAsState()



    Column(
        modifier = Modifier.fillMaxSize().padding(top = 130.dp).padding(bottom = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        if(tasks.isEmpty()){
            BlankScreen()
        }else{
            LazyColumn {
                items(tasks) { task->
                    ItemTaskCompleted(task, viewModel)

                }
            }


        }


    }


}


@Composable

fun ItemTaskCompleted(task: CompletedDataClass, viewModel: TaskViewModel){
    var showAlert by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<CompletedDataClass?>(null) }



    if(showAlert && selectedTask !=null){
        AlertaCompleted(
            onConfirm = {viewModel.deleteCompleted(task)},
            onDismiss = {
                showAlert=false
                selectedTask=null
            }
        )
    }


    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Yellow
        ),
        modifier = Modifier.fillMaxWidth(0.99f)
            .wrapContentSize()
            .padding(10.dp)
    ) {
        Column(

            modifier= Modifier.align(alignment = Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        ){
            Text(text = task.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .align(Alignment.CenterHorizontally)
        ){
            Image(painter = painterResource(id = R.drawable.check_outline),
                contentDescription = "check",
                modifier = Modifier.weight(0.20f)
                    .fillMaxHeight(0.2f)
                    .align(Alignment.CenterVertically)
                    .padding(15.dp)
            )
            Text(text=task.description, fontSize = 20.sp,
                modifier = Modifier.weight(0.6f)
                    .padding(15.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Justify
            )



            IconButton(onClick = {
                selectedTask=task
                showAlert=true
            }, modifier = Modifier.weight(0.20f)
                .fillMaxHeight(0.2f)
                .align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }



        }
    }



}


@Composable
fun AlertaCompleted(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit

){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Task") },

        dismissButton = {
            Button(onClick = {onDismiss()},
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                )) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {onConfirm()},
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )) {
                Text("Delete")
            }
        },
    )

}