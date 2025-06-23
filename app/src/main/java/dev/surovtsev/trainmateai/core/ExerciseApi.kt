package dev.surovtsev.trainmateai.core

import retrofit2.http.GET

interface ExerciseApi {

    @GET("exercises")
    suspend fun fetchExercises(): List<ExerciseDto>

    @GET("exercise_media.json")
    suspend fun fetchMedia(): List<MediaDto>
}


