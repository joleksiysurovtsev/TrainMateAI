/* ────────── ExerciseViewModel.kt ────────── */
package dev.surovtsev.trainmateai.feature.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.surovtsev.trainmateai.core.repository.ExerciseRepository
import dev.surovtsev.trainmateai.feature.exercises.domain.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    /** Data for the list */
    val exercises: StateFlow<List<Exercise>> = repository.exercisesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** The status of the update button/indicator */
    sealed interface RefreshState {
        object Idle     : RefreshState
        object Loading  : RefreshState
        data class Error(val msg: String) : RefreshState
    }

    private val _refresh = MutableStateFlow<RefreshState>(RefreshState.Idle)
    val refresh: StateFlow<RefreshState> = _refresh.asStateFlow()

    /** We call from the UI when pressing the button */
    fun onRefreshClick() {
        if (_refresh.value == RefreshState.Loading) return
        viewModelScope.launch {
            _refresh.value = RefreshState.Loading
            try {
                repository.refreshFromRemote()
                _refresh.value = RefreshState.Idle
            } catch (e: Exception) {
                _refresh.value = RefreshState.Error(
                    e.localizedMessage ?: "Failed to download data"
                )
            }
        }
    }

    fun consumeError() {
        if (_refresh.value is RefreshState.Error) {
            _refresh.value = RefreshState.Idle
        }
    }
}
