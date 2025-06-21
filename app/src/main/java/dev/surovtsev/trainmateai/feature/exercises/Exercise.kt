package dev.surovtsev.trainmateai.feature.exercises

data class Exercise(
    val id: String,
    val name: String,
    val description: String,
    val category: ExerciseCategory,
    val imageUrl: String? = null
)
