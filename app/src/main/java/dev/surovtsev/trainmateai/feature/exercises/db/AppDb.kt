package dev.surovtsev.trainmateai.feature.exercises.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.surovtsev.trainmateai.feature.exercises.converter.CategoryConverter
import dev.surovtsev.trainmateai.feature.exercises.converter.MediaKindConverter
import dev.surovtsev.trainmateai.feature.exercises.dao.ExerciseDao
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseEntity
import dev.surovtsev.trainmateai.feature.exercises.domain.UiExerciseMediaEntity

@Database(
    entities = [ExerciseEntity::class, UiExerciseMediaEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CategoryConverter::class, MediaKindConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}
