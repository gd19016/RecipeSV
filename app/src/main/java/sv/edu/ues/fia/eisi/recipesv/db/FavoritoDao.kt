package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritoDao {
    @Query("SELECT * FROM favorito")
    fun getAll():LiveData<List<FavoritoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorito: FavoritoEntity)

    @Update
    suspend fun update(favorito: FavoritoEntity)

    @Delete
    suspend fun delete(favorito: FavoritoEntity)

    @Query("DELETE FROM favorito")
    suspend fun deleteAll()
}