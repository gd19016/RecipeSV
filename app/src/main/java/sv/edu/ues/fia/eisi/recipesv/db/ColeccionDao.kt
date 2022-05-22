package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ColeccionDao {

    @Query("SELECT * FROM colecciones")
    fun getAll(): LiveData<List<ColeccionEntity>>

    @Query("select '' union all SELECT id_coleccion || '-' || nombre FROM colecciones")
    suspend fun getAllForSpinner(): Array<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coleccion: ColeccionEntity)

    @Update
    suspend fun update(coleccion: ColeccionEntity)

    @Delete
    suspend fun delete(coleccion: ColeccionEntity)

    @Query("DELETE FROM colecciones")
    suspend fun deleteAll()
}