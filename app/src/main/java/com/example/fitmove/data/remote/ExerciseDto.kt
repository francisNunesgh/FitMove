package com.example.fitmove.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponse(
    val data: List<ExerciseDto>
)

@Serializable
data class ExerciseDto(
    val id: String,
    val name: String,

    @SerialName("primary_muscle")
    val primaryMuscle: String,

    val equipment: List<String>,

    val cues: String
)