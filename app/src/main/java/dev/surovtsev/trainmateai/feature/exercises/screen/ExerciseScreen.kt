package dev.surovtsev.trainmateai.feature.exercises.screen

/* ---------- imports ---------- */
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.surovtsev.trainmateai.feature.exercises.ExerciseViewModel
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseCategory

/* ---------- константа высоты NavBar ---------- */
private val NavBarHeight = 90.dp     // такая же, как в GlassBottomNavigationBar

/* ---------- Saver для категории ---------- */
private const val NONE = "__NONE__"
private val CategorySaver: Saver<ExerciseCategory?, String> = Saver(
    save    = { it?.displayName ?: NONE },
    restore = { key -> if (key == NONE) null else ExerciseCategory.BY_NAME[key] }
)

/* ---------- экран ---------- */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    /* === состояния === */
    val exercises    by viewModel.exercises.collectAsState()
    val refreshState by viewModel.refresh.collectAsState()
    var selected     by rememberSaveable(stateSaver = CategorySaver) {
        mutableStateOf<ExerciseCategory?>(null)
    }

    /* snackbar для ошибок */
    val snackbarHost = remember { SnackbarHostState() }
    LaunchedEffect(refreshState) {
        (refreshState as? ExerciseViewModel.RefreshState.Error)?.let { err ->
            snackbarHost.showSnackbar(err.msg)        // показать
            viewModel.consumeError()                  // ❗ сбросить
        }
    }

    /* ===== UI ===== */
    Box(Modifier.fillMaxSize()) {

        /* --- фильтр + сетка --- */
        Column(Modifier.fillMaxSize()) {

            /* ►►► Бэйджи + Refresh */
            ExerciseBadges(
                selected            = selected,
                onCategorySelected  = { selected = it },
                isRefreshing        = refreshState is ExerciseViewModel.RefreshState.Loading,
                onRefreshClick      = {
                    if (refreshState !is ExerciseViewModel.RefreshState.Loading) {
                        viewModel.onRefreshClick()
                    }
                }
            )

            val shown = when (selected) {
                null -> exercises
                else -> exercises.filter { it.category.root() == selected }
            }
            ExerciseGrid(shown)
        }

        /* ---- Snackbar ---- */
        SnackbarHost(
            hostState = snackbarHost,
            modifier  = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = NavBarHeight + 56.dp)
        )
    }
}

/* ---------- helper: корневая категория ---------- */
private fun ExerciseCategory.root(): ExerciseCategory =
    generateSequence(this) { it.parent }.last()
