package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TipoDao {
    @Query("SELECT * FROM tipo")
    fun getAll(): LiveData<List<TipoEntity>>

    @Query("SELECT id_tipo || '-' || nombre FROM tipo")
    suspend fun getAllForSpinnerTipo(): Array<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tipo: TipoEntity)

    @Update
    suspend fun update(tipo: TipoEntity)

    @Delete
    suspend fun delete(tipo: TipoEntity)

    @Query("DELETE FROM tipo")
    suspend fun deleteAll()
}