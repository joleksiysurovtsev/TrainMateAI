package dev.surovtsev.trainmateai.feature.exercises.screen

/* ---------- imports ---------- */
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.surovtsev.trainmateai.feature.exercises.ExerciseCategory
import dev.surovtsev.trainmateai.feature.exercises.ExerciseViewModel

/* ---------- Saver ---------- */
private const val NONE = "__NONE__"
private val CategorySaver: Saver<ExerciseCategory?, String> = Saver(
    save    = { it?.displayName ?: NONE },
    restore = { key -> if (key == NONE) null else ExerciseCategory.Companion.BY_NAME[key] }
)

/* ---------- screen ---------- */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseScreen(viewModel: ExerciseViewModel = ExerciseViewModel()) {

    val exercises by viewModel.exercises.collectAsState()
    var selected by rememberSaveable(stateSaver = CategorySaver) {
        mutableStateOf<ExerciseCategory?>(null)
    }

    /* ===== list of exercises ===== */
    val shown = remember(selected, exercises) {
        when (selected) {
            null -> exercises
            else -> exercises.filter { it.category.root() == selected }
        }
    }

    Column(Modifier.fillMaxSize()) {
        ExerciseBadges(selected = selected, onCategorySelected = { selected = it })
        ExerciseGrid(shown)
    }
}

/* ---------- helper: root parent category ---------- */
private fun ExerciseCategory.root(): ExerciseCategory =
    generateSequence(this) { it.parent }.last()
