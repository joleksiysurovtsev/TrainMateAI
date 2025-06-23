package dev.surovtsev.trainmateai.core.repository

import dev.surovtsev.trainmateai.core.ExerciseApi
import dev.surovtsev.trainmateai.core.dao.ExerciseDao
import dev.surovtsev.trainmateai.feature.exercises.domain.Exercise
import dev.surovtsev.trainmateai.feature.exercises.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(
    private val dao: ExerciseDao,
    private val api: ExerciseApi
) {
    /* Локальный поток данных */
    val exercisesFlow: Flow<List<Exercise>> = dao.observeAll()

    /**
     * Пробуем скачать каталог упражнений.
     * Ошибки наружу, чтобы VM могла показать пользователю.
     */
    suspend fun refreshFromRemote() {
        val list = api.fetchExercises().map { it.toEntity() }
        dao.insertAll(list)
    }
}
