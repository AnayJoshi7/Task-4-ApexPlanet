package com.anay.fitnesstracker.data.model

data class Workout(
    val exercises: Int = 0,
    val isToday: Boolean = false,
    val muscles: String = "",
    val title: String = ""
)