package dev.surovtsev.trainmateai.core

import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseCategory
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto(
    val id: Long,
    val name: String,
    val description: String,
    val category: ExerciseCategory,
)