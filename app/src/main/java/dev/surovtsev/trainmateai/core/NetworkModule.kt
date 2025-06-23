package dev.surovtsev.trainmateai.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.surovtsev.trainmateai.core.dao.ExerciseDao
import dev.surovtsev.trainmateai.core.repository.ExerciseRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://trainmateaidataprovider-production.up.railway.app/api/"

    val contentType = "application/json".toMediaType()

    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    @Provides
    @Singleton
    fun provideExerciseApi(retrofit: Retrofit): ExerciseApi =
        retrofit.create(ExerciseApi::class.java)

    @Provides
    @Singleton
    fun provideExerciseRepository(
        dao: ExerciseDao,
        api: ExerciseApi
    ): ExerciseRepository = ExerciseRepository(dao, api)
}