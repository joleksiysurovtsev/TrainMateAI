/* ────────── ExerciseDao.kt ────────── */
package dev.surovtsev.trainmateai.feature.exercises.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ExerciseDao {

    /* постраничная загрузка для Paging 3 */
    @Query("SELECT * FROM exercises ORDER BY name")
    fun pagingSource(): PagingSource<Int, ExerciseEntity>

    /* поток всех упражнений в алфавитном порядке */
    @Query("SELECT * FROM exercises ORDER BY name")
    fun observeAll(): Flow<List<ExerciseEntity>>

    /* массовое сохранение/обновление */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ExerciseEntity>)

    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Query("INSERT INTO exercises(name, description, category) VALUES(:name, :description, :category)")
    suspend fun insert(name: String, description: String, category: String)
}


class UuidConverter {
    @TypeConverter
    fun fromUuid(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun toUuid(value: String?): UUID? = value?.let(UUID::fromString)
}


@Database(
    entities = [ExerciseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}