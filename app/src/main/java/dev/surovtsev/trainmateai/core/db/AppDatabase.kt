package dev.surovtsev.trainmateai.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.surovtsev.trainmateai.core.dao.ExerciseDao
import dev.surovtsev.trainmateai.feature.exercises.domain.Exercise

@Database(
    entities = [Exercise::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}