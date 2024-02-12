package it.saimao.diario.data

import kotlinx.coroutines.flow.Flow


class DiarioRepository(private val diarioDao: DiarioDao) {
    suspend fun insert(diarioEntity: DiarioEntity) {
        diarioDao.insertDiario(diarioEntity)
    }

    suspend fun update(diarioEntity: DiarioEntity) {
        diarioDao.updateDiario(diarioEntity)
    }

    suspend fun delete(diarioEntity: DiarioEntity) {
        diarioDao.deleteDiario(diarioEntity)
    }

    fun getAllDiario(): Flow<List<DiarioEntity>> {
        return diarioDao.getAllDiario()
    }

    fun getDiarioById(id: Int): Flow<DiarioEntity> = diarioDao.getDiarioById(id)
}