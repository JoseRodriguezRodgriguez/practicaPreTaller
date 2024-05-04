package com.jarr.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jarr.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyActivity()
                }
            }
        }
    }
}

data class Task(var title: String, var desc: String)

@Composable
fun MyActivity() {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val tasks = remember { mutableListOf<Task>() }
    var showAddTask by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(tasks.size){
                    index -> TaskCard(task = tasks[index])
            }
        }

        FloatingActionButton(
            onClick = { showAddTask = !showAddTask }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if(showAddTask) {
            Dialog(onDismissRequest = { showAddTask = false }) {
                Column(modifier = Modifier
                    .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(text = "Actividad") }
                    )

                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it},
                        label = { Text(text = "Descripción") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                tasks.add(Task(title, desc))
                                title = ""
                                desc = ""
                                showAddTask = false
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(text = "Agregar")
                        }

                        Button(
                            onClick = { showAddTask = false }
                        ) {
                            Text(text = "Cancelar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = task.title, fontWeight = FontWeight.Bold)
            Text(text = task.desc, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Preview
@Composable
fun MyActivityPreview() {
    MyActivity()
}