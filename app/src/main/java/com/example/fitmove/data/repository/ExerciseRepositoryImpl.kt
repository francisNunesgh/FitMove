package com.example.fitmove.data.repository

import com.example.fitmove.data.mapper.toDomain
import com.example.fitmove.data.remote.ExerciseApiService
import com.example.fitmove.domain.model.Exercise
import com.example.fitmove.domain.repository.ExerciseRepository
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val api: ExerciseApiService
) : ExerciseRepository {

    override suspend fun getExercises(): List<Exercise> {
        return api.getExercises().data.map { dto ->
            dto.toDomain()
        }
    }
}



