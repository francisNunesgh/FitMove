package com.example.fitmove.data.remote

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

object RetrofitClient {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://exercise-api.com/v1/")
        .addConverterFactory(
            json.asConverterFactory(
                "application/json".toMediaType()
            )
        )
        .build()

    val exerciseApi: ExerciseApiService =
        retrofit.create(ExerciseApiService::class.java)
}