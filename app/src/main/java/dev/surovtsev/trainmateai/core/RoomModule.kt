package dev.surovtsev.trainmateai.core

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.surovtsev.trainmateai.core.dao.ExerciseDao
import dev.surovtsev.trainmateai.core.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext ctx: Context
    ): AppDatabase =
        Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            "trainmate.db"
        ).build()

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()
}
