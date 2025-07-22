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

    val exercisesFlow: Flow<List<Exercise>> = dao.observeAll()

    suspend fun refreshFromRemote() {
        val list: List<Exercise> = api.fetchExercises().map { it.toEntity() }
        dao.insertAll(list)
    }
}
