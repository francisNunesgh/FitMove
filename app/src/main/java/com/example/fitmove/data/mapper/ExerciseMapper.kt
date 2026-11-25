package com.example.fitmove.data.mapper

import com.example.fitmove.data.remote.ExerciseDto
import com.example.fitmove.domain.model.Exercise

fun ExerciseDto.toDomain(): Exercise {
    return Exercise(
        id = id,
        name = name,
        primaryMuscle = primaryMuscle,
        equipment = equipment,
        cues = cues
    )
}
