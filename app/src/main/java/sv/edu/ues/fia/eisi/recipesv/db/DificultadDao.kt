package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DificultadDao {
    @Query("SELECT * FROM dificultad")
    fun getAll(): LiveData<List<DificultadEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dificultad: DificultadEntity)

    @Update
    suspend fun update(dificultad: DificultadEntity)

    @Delete
    suspend fun delete(dificultad: DificultadEntity)

    @Query("DELETE FROM dificultad")
    suspend fun deleteAll()
}