package com.example.fitmove.domain.usecase

import com.example.fitmove.domain.model.Exercise
import com.example.fitmove.domain.repository.ExerciseRepository

class GetExercisesUseCase(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(): List<Exercise> {
        return repository.getExercises()
    }
}
