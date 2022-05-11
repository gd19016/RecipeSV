package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecetaDao {
    @Query("SELECT * FROM receta")
    fun getAll(): LiveData<List<RecetaEntity>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(receta: RecetaEntity)
    @Update
    suspend fun update(receta: RecetaEntity)
    @Delete
    suspend fun delete(receta: RecetaEntity)
    @Query("DELETE FROM receta")
    suspend fun deleteAll()
}