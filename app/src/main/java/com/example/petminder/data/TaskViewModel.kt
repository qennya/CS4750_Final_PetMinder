package com.example.petminder.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime

class TaskViewModel : ViewModel() {

    var selectedDate by mutableStateOf(LocalDate.now())
        private set

    var tasks by mutableStateOf(sampleTasks())
        private set

    fun setDate(d: LocalDate) { selectedDate = d }

    fun tasksFor(date: LocalDate): List<Task> =
        tasks.filter { it.date == date }.sortedBy { it.time }

    fun upsert(task: Task) {
        tasks = tasks.toMutableList().apply {
            val i = indexOfFirst { it.id == task.id }
            if (i >= 0) set(i, task) else add(task)
        }
    }

    fun delete(id: String) {
        tasks = tasks.filterNot { it.id == id }
    }

    // ✅ NEW FUNCTION — handles "Skip for today"
    fun skipTaskForToday(id: String?) {
        if (id == null) return
        tasks = tasks.map { t ->
            if (t.id == id) t.copy(date = t.date.plusDays(1)) else t
        }
    }
}

private fun sampleTasks(): List<Task> {
    val now = LocalDate.now()
    return listOf(
        Task(
            title = "Morning Feeding",
            description = "Details here about idk...",
            date = now,
            time = LocalTime.of(8, 0),
            assignee = "Jeannette"
        ),
        Task(
            title = "Morning Walk",
            description = "Details here about idk...",
            date = now,
            time = LocalTime.of(8, 30),
            assignee = "Carlos"
        ),
        Task(
            title = "Evening Feeding",
            description = "Details here about idk...",
            date = now,
            time = LocalTime.of(20, 0)
        ),
        Task(
            title = "Evening Walk",
            description = "Details here about idk...",
            date = now,
            time = LocalTime.of(20, 30)
        )
    )
}
