package com.example.fitmove.data.repository

import com.example.fitmove.data.remote.ExerciseDto
import com.example.fitmove.data.remote.RetrofitClient

class ExerciseRepository {

    private val api = RetrofitClient.exerciseApi

    suspend fun getExercises(): List<ExerciseDto> {
        return api.getExercises().data
    }
}
