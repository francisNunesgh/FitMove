package com.example.fitmove.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApiService {

    @GET("exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int = 20
    ): ExerciseResponse
}