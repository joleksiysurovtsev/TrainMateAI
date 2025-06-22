package dev.surovtsev.trainmateai.feature.exercises.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class UiExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val category: ExerciseCategory,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
)

