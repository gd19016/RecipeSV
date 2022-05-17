package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ColeccionRecetaDao {
    @Query("SELECT * FROM coleccion_recetas")
    fun getAll(): LiveData<List<ColeccionRecetasEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(colReceta: ColeccionRecetasEntity)

    @Update
    suspend fun update(colReceta: ColeccionRecetasEntity)

    @Delete
    suspend fun delete(colReceta: ColeccionRecetasEntity)

    @Query("DELETE FROM coleccion_recetas")
    suspend fun deleteAll()
}