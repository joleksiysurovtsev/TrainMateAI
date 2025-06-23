package dev.surovtsev.trainmateai.core

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.surovtsev.trainmateai.feature.exercises.dao.AppDatabase
import dev.surovtsev.trainmateai.feature.exercises.dao.ExerciseDao
import javax.inject.Singleton

// RoomModule.kt
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides @Singleton
    fun provideDatabase(
        @ApplicationContext ctx: Context
    ): AppDatabase =
        Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,       // вот так Room понимает тип базы
            "trainmate.db"
        ).build()

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao =
        db.exerciseDao()
}
