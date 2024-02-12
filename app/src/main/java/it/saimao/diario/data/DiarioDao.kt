package it.saimao.diario.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiarioDao {
    @Insert
    suspend fun insertDiario(diarioEntity: DiarioEntity)

    @Update
    suspend fun updateDiario(diarioEntity: DiarioEntity)

    @Delete
    suspend fun deleteDiario(diarioEntity: DiarioEntity)

    @Query("SELECT * FROM diario ORDER BY updated DESC;")
    fun getAllDiario(): Flow<List<DiarioEntity>>

    @Query("SELECT * FROM diario WHERE id = :id")
    fun getDiarioById(id: Int): Flow<DiarioEntity>
}