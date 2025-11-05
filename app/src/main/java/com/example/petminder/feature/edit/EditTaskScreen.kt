package com.example.petminder.feature.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petminder.data.Task
import com.example.petminder.data.TaskViewModel
import java.time.*
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    vm: TaskViewModel,
    taskId: String?,
    onDone: () -> Unit,
    onDelete: () -> Unit
) {
    val existing = remember(taskId, vm.tasks) {
        vm.tasks.find { it.id == taskId }
    }

    var title by remember { mutableStateOf(existing?.title ?: "") }
    var desc by remember { mutableStateOf(existing?.description ?: "") }
    var assignee by remember { mutableStateOf(existing?.assignee ?: "") }
    var note by remember { mutableStateOf(existing?.note ?: "") }
    var date by remember { mutableStateOf(existing?.date ?: vm.selectedDate) }
    var time by remember { mutableStateOf(existing?.time ?: LocalTime.of(8, 0)) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val dateFmt = remember { DateTimeFormatter.ofPattern("MMM d, yyyy") }
    val timeFmt = remember { DateTimeFormatter.ofPattern("h:mm a") }

    // --- UI ---
    Column(Modifier.padding(16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = {
                existing?.let { vm.delete(it.id) }
                onDelete()
            }) { Text("Delete") }

            Button(onClick = {
                val updated = (existing ?: Task(
                    title = title.ifBlank { "Untitled" },
                    date = date,
                    time = time
                )).copy(
                    title = title,
                    description = desc,
                    date = date,
                    time = time,
                    assignee = assignee.ifBlank { null },
                    note = note
                )
                vm.upsert(updated)
                onDone()
            }) { Text("Save") }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // --- Date Picker ---
        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.CalendarMonth, contentDescription = "Pick date")
            Spacer(Modifier.width(8.dp))
            Text(date.format(dateFmt))
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("OK")
                    }
                }
            ) {
                val state = rememberDatePickerState(initialSelectedDateMillis = date.toEpochDay() * 86400000)
                DatePicker(state = state)
                LaunchedEffect(state.selectedDateMillis) {
                    state.selectedDateMillis?.let {
                        date = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // --- Time Picker ---
        OutlinedButton(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.AccessTime, contentDescription = "Pick time")
            Spacer(Modifier.width(8.dp))
            Text(time.format(timeFmt))
        }
        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = { showTimePicker = false }) { Text("OK") }
                }
            ) {
                val state = rememberTimePickerState(
                    initialHour = time.hour,
                    initialMinute = time.minute
                )
                TimePicker(state = state)
                LaunchedEffect(state.hour, state.minute) {
                    time = LocalTime.of(state.hour, state.minute)
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = assignee,
            onValueChange = { assignee = it },
            label = { Text("Completed By") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4
        )

        Spacer(Modifier.height(12.dp))

        // --- Skip for Today ---
        OutlinedButton(
            onClick = {
                // Move task date to tomorrow
                date = date.plusDays(1)
                vm.skipTaskForToday(taskId)
                onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Skip for today")
        }
    }
}
