package dev.surovtsev.trainmateai.feature.exercises.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.surovtsev.trainmateai.feature.exercises.domain.UiExerciseEntity

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises ORDER BY name")
    fun pagingSource(): PagingSource<Int, UiExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UiExerciseEntity>)
}