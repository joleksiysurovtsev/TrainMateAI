package dev.surovtsev.trainmateai.feature.exercises.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_media")
data class UiExerciseMediaEntity(
    @PrimaryKey val id: String,
    val exerciseId: String,
    val kind: MediaKind,
    val url: String,
    val order: Int = 0
)