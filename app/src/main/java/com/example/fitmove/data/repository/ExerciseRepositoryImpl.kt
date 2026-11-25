package com.example.fitmove.data.repository


import com.example.fitmove.data.mapper.toDomain
import com.example.fitmove.data.remote.RetrofitClient
import com.example.fitmove.domain.model.Exercise
import com.example.fitmove.domain.repository.ExerciseRepository

class ExerciseRepositoryImpl : ExerciseRepository {

    private val api = RetrofitClient.exerciseApi

    override suspend fun getExercises(): List<Exercise> {
        return api.getExercises().data.map { dto ->
            dto.toDomain()
        }
    }
}

