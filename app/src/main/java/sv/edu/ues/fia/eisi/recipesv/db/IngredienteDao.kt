package sv.edu.ues.fia.eisi.recipesv.db


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IngredienteDao {
    @Query("SELECT * FROM ingrediente")
    fun getAll(): LiveData<List<IngredienteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingrediente: IngredienteEntity)

    @Update
    suspend fun update(ingrediente: IngredienteEntity)

    @Delete
    suspend fun delete(ingrediente: IngredienteEntity)

    @Query("DELETE FROM ingrediente")
    suspend fun deleteAll()
}