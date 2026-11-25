package com.example.fitmove.domain.model

data class Exercise(
    val id: String,
    val name: String,
    val primaryMuscle: String,
    val equipment: List<String>,
    val cues: String
)
