package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HistoricoDao {
    @Query("SELECT * FROM historico")
    fun getAll(): LiveData<List<HistoricoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(historico: HistoricoEntity)

    @Update
    suspend fun update(historico: HistoricoEntity)

    @Delete
    suspend fun delete(historico: HistoricoEntity)

    @Query("DELETE FROM historico")
    suspend fun deleteAll()
}