package dev.surovtsev.trainmateai.core.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.surovtsev.trainmateai.feature.exercises.domain.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises ORDER BY name")
    fun pagingSource(): PagingSource<Int, Exercise>

    @Query("SELECT * FROM exercises ORDER BY name")
    fun observeAll(): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Exercise>)

    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<Exercise>>

    @Query("INSERT INTO exercises(name, description, category, imageurl) VALUES(:name, :description, :category, :imageurl)")
    suspend fun insert(name: String, description: String, category: String, imageurl: String)
}

