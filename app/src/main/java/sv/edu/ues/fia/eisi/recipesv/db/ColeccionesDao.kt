package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ColeccionesDao {

    @Query("SELECT * FROM colecciones")
    fun getAll(): LiveData<List<ColeccionesEntity>>

    @Query("select '' union all SELECT id_coleccion || '-' || nombre FROM colecciones")
    suspend fun getAllForSpinner(): Array<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coleccion: ColeccionesEntity)

    @Update
    suspend fun update(coleccion: ColeccionesEntity)

    @Delete
    suspend fun delete(coleccion: ColeccionesEntity)

    @Query("DELETE FROM colecciones")
    suspend fun deleteAll()
}