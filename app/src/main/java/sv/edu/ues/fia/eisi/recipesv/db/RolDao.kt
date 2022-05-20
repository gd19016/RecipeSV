package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RolDao {
    @Query("SELECT * FROM rol")
    fun getAll(): LiveData<List<RolEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rol: RolEntity)

    @Update
    suspend fun update(rol: RolEntity)

    @Delete
    suspend fun delete(rol: RolEntity)

    @Query("DELETE FROM rol")
    suspend fun deleteAll()
}