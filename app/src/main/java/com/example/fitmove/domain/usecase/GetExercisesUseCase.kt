package com.example.fitmove.domain.usecase

import com.example.fitmove.domain.model.Exercise
import com.example.fitmove.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(): List<Exercise> {
        return repository.getExercises()
    }
}
