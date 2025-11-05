package com.example.petminder.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petminder.data.Task
import java.time.format.DateTimeFormatter

@Composable
fun TaskCard(task: Task, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val timeFmt = DateTimeFormatter.ofPattern("h:mm a")
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(task.time.format(timeFmt), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.width(76.dp))
        ElevatedCard(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = .35f))
        ) {
            Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(task.title, style = MaterialTheme.typography.titleMedium)
                    if (task.description.isNotBlank())
                        Text(task.description, style = MaterialTheme.typography.bodyMedium)
                }
                if (!task.assignee.isNullOrBlank()) {
                    AssistChip(onClick = onClick, label = { Text(task.assignee!!) })
                }
                IconButton(onClick = onClick) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More")
                }
            }
        }
    }
}
