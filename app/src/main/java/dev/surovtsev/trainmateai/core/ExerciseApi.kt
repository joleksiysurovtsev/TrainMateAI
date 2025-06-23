package dev.surovtsev.trainmateai.core

import retrofit2.http.GET

/** ---------------- 2. Retrofit API ---------------- */

interface ExerciseApi {

    @GET("exercises")              // → https://your.cdn.com/exercises.json
    suspend fun fetchExercises(): List<ExerciseDto>

    @GET("exercise_media.json")
    suspend fun fetchMedia(): List<MediaDto>

}


