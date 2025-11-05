package com.example.petminder.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petminder.data.Task
import com.example.petminder.data.TaskViewModel
import com.example.petminder.ui.components.TaskCard
import com.example.petminder.ui.components.TopDateStrip
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: TaskViewModel,
    onAdd: () -> Unit,
    onOpenTask: (Task) -> Unit
) {
    val selected = vm.selectedDate
    val tasks = vm.tasksFor(selected)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
        ) {
            Text("PetMinder", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            TopDateStrip(selected = selected, onPick = { vm.setDate(it) })
            Spacer(Modifier.height(16.dp))
            Text("Today's Schedule", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if (tasks.isEmpty()) {
                Text("No tasks for ${selected}. Tap + to add.")
            } else {
                tasks.forEach { t ->
                    TaskCard(task = t, onClick = { onOpenTask(t) })
                }
            }
        }
    }
}
