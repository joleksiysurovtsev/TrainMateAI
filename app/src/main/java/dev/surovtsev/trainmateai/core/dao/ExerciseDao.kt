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

    /* постраничная загрузка для Paging 3 */
    @Query("SELECT * FROM exercises ORDER BY name")
    fun pagingSource(): PagingSource<Int, Exercise>

    /* поток всех упражнений в алфавитном порядке */
    @Query("SELECT * FROM exercises ORDER BY name")
    fun observeAll(): Flow<List<Exercise>>

    /* массовое сохранение/обновление */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Exercise>)

    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<Exercise>>

    @Query("INSERT INTO exercises(name, description, category) VALUES(:name, :description, :category)")
    suspend fun insert(name: String, description: String, category: String)
}

