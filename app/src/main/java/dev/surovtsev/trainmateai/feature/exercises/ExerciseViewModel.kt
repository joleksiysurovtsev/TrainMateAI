package dev.surovtsev.trainmateai.feature.exercises


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExerciseViewModel : ViewModel() {

    private val _exercises = MutableStateFlow(sampleExercises())
    val exercises: StateFlow<List<Exercise>> = _exercises

    /** Демонстрационные данные. */
    private fun sampleExercises(): List<Exercise> = listOf(
        Exercise(
            id          = "1",
            name        = "Push-Up",
            description = "Body-weight chest exercise",
            category    = ExerciseCategory.Chest
        ),
        Exercise(
            id          = "2",
            name        = "Back Squat",
            description = "Legs and glutes builder",
            category    = ExerciseCategory.Legs.Quads
        ),
        Exercise(
            id          = "3",
            name        = "Pull-Up",
            description = "Back and biceps developer",
            category    = ExerciseCategory.Back
        ),
    )
}


