package dev.surovtsev.trainmateai.feature.exercises

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

/* ---------- устойчивый Saver ---------- */
private const val NONE = "__NONE__"

private val CategorySaver: Saver<ExerciseCategory?, String> = Saver(
    save    = { it?.displayName ?: NONE },
    restore = { key -> if (key == NONE) null else ExerciseCategory.BY_NAME[key] }
)

/* ---------- экран ---------- */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseScreen(viewModel: ExerciseViewModel = ExerciseViewModel()) {

    val exercises by viewModel.exercises.collectAsState()
    var selected by rememberSaveable(stateSaver = CategorySaver) {
        mutableStateOf<ExerciseCategory?>(null)
    }

    val hazeState   = remember { HazeState() }
    val badgeShape: Shape = RoundedCornerShape(50)
    val categories  = remember(exercises) { exercises.map { it.category }.distinct() }

    Column(Modifier.fillMaxSize()) {

        /* ===== бейджи ===== */
        LazyRow(contentPadding = PaddingValues(start = 16.dp, top = 12.dp, bottom = 8.dp)) {
            items(categories, key = { it.displayName }) { category ->
                val isSelected = category == selected
                CategoryBadge(
                    text   = category.displayName,
                    selected = isSelected,
                    hazeState = hazeState,
                    shape = badgeShape
                ) { selected = if (isSelected) null else category }
            }
        }

        /* ===== упражнения ===== */
        val shown = remember(selected, exercises) {
            selected?.let { cat -> exercises.filter { it.category == cat } } ?: exercises
        }

        LazyVerticalGrid(
            columns               = GridCells.Adaptive(minSize = 160.dp),
            contentPadding        = PaddingValues(16.dp),
            verticalArrangement   = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier              = Modifier.fillMaxSize()
        ) {
            items(shown, key = { it.id }) { ExerciseTile(it) }
        }
    }
}

/* ---------- UI-компоненты ---------- */

@Composable
private fun CategoryBadge(
    text: String,
    selected: Boolean,
    hazeState: HazeState,
    shape: Shape,
    onClick: () -> Unit
) {
    val bg by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
        else
            MaterialTheme.colorScheme.surface.copy(alpha = 0.20f)
    )

    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(shape)
            .background(bg)
            .hazeChild(
                state = hazeState,
                shape = shape,
                style = HazeStyle(
                    blurRadius  = 60.dp,
                    tint        = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.25f),
                    noiseFactor = 0.15f
                )
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text  = text,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ExerciseTile(exercise: Exercise) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(exercise.name,        style = MaterialTheme.typography.titleMedium)
            Text(exercise.description, style = MaterialTheme.typography.bodyMedium)
            Text(
                exercise.category.displayName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
