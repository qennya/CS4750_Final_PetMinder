package com.example.petminder.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TopDateStrip(selected: LocalDate, onPick: (LocalDate) -> Unit) {
    val start = selected.minusDays(3)
    val days = (0..6).map { start.plusDays(it.toLong()) }
    val dow = DateTimeFormatter.ofPattern("EEE")
    val dayNum = DateTimeFormatter.ofPattern("d")

    Column(Modifier.fillMaxWidth()) {
        Text(text = selected.format(DateTimeFormatter.ofPattern("MMMM d")), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            days.forEach { d ->
                FilterChip(
                    selected = d == selected,
                    onClick = { onPick(d) },
                    label = { Column { Text(d.format(dow).uppercase()); Text(d.format(dayNum)) } }
                )
            }
        }
    }
}
