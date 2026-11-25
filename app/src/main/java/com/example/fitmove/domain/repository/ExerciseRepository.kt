package com.example.fitmove.domain.repository

import com.example.fitmove.domain.model.Exercise

interface ExerciseRepository {
    suspend fun getExercises(): List<Exercise>
}
