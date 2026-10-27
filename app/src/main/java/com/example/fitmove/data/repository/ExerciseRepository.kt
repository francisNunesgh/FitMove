package com.example.fitmove.data.repository

import com.example.fitmove.data.remote.ExerciseDto
import com.example.fitmove.data.remote.RetrofitClient

interface ExerciseRepository {
    suspend fun getExercises(): List<ExerciseDto>
}

