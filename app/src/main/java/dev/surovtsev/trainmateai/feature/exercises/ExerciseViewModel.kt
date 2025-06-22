package dev.surovtsev.trainmateai.feature.exercises

import androidx.lifecycle.ViewModel
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseCategory
import dev.surovtsev.trainmateai.feature.exercises.domain.UiExerciseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExerciseViewModel : ViewModel() {

    // ---------------- state ----------------
    private val _exercises = MutableStateFlow(sampleExercises())
    val exercises: StateFlow<List<UiExerciseEntity>> = _exercises

    // ---------------- demo-data ----------------
    private fun sampleExercises(): List<UiExerciseEntity> = listOf(
        UiExerciseEntity(
            id = "1",
            name = "Push-Up",
            description = "Body-weight chest exercise",
            category = ExerciseCategory.Chest,
        ),
        UiExerciseEntity(
            id = "2",
            name = "Back Squat",
            description = "Legs and glutes builder",
            category = ExerciseCategory.Quads
        ),
        UiExerciseEntity(
            id = "3",
            name = "Pull-Up",
            description = "Back and biceps developer",
            category = ExerciseCategory.Back
        ),
        UiExerciseEntity(
            id = "4",
            name = "Biceps Curl",
            description = "Isolation for biceps",
            category = ExerciseCategory.Biceps
        )
    )
}