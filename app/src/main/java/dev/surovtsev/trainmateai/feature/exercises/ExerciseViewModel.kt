/* ────────── ExerciseViewModel.kt ────────── */
package dev.surovtsev.trainmateai.feature.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseEntity
import dev.surovtsev.trainmateai.feature.exercises.repository.ExerciseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    /* ───── публичное состояние для UI ───── */
    val exercises: StateFlow<List<ExerciseEntity>> =
        repository.exercisesFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        /* первый запуск – обновляем каталог */
        viewModelScope.launch {
            repository.refreshFromRemote()
        }
    }
}
