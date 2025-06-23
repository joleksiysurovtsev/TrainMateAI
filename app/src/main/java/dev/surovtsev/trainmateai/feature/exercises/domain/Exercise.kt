package dev.surovtsev.trainmateai.feature.exercises.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)val id: Long = 0,
    val name: String,
    val description: String,
    val category: ExerciseCategory,
)

