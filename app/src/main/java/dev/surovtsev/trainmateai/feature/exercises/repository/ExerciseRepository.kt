/* ────────── ExerciseRepository.kt ────────── */
package dev.surovtsev.trainmateai.feature.exercises.repository

import dev.surovtsev.trainmateai.core.ExerciseApi
import dev.surovtsev.trainmateai.feature.exercises.dao.ExerciseDao
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseEntity
import dev.surovtsev.trainmateai.feature.exercises.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(
    private val dao: ExerciseDao,
    private val api: ExerciseApi         // ⚡ инжектируем API напрямую
) {
    /* поток локальных данных */
    val exercisesFlow: Flow<List<ExerciseEntity>> = dao.observeAll()

    /* синхронизация БД с бекендом */
    suspend fun refreshFromRemote() {
        val list = api.fetchExercises().map { it.toEntity() }
        dao.insertAll(list)
    }
}
