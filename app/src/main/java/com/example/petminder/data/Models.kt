package com.example.petminder.data

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val date: LocalDate,
    val time: LocalTime,
    val assignee: String? = null,
    val note: String = ""
)
