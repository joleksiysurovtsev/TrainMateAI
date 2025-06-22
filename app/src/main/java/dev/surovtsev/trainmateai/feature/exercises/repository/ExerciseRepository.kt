package dev.surovtsev.trainmateai.feature.exercises.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import dev.surovtsev.trainmateai.core.ExerciseApi
import dev.surovtsev.trainmateai.feature.exercises.dao.ExerciseDao
import dev.surovtsev.trainmateai.feature.exercises.mapper.toDto
import dev.surovtsev.trainmateai.feature.exercises.mapper.toEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(
    private val dao: ExerciseDao
) {
    fun pagingFlow(pageSize: Int = 30) =
        Pager(PagingConfig(pageSize)) { dao.pagingSource() }
            .flow
            .map { pagingData -> pagingData.map { it.toDto() } }

    /** подтянуть свежий каталог с бэкенда */
    suspend fun refreshFromRemote(api: ExerciseApi) {
        val list = api.fetchExercises().map { it.toEntity() }
        dao.insertAll(list)
    }
}
