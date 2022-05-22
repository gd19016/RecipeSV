package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ComentarioDao {
    @Query("SELECT * FROM comentario")
    fun getAll():LiveData<List<ComentarioEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comentario: ComentarioEntity)

    @Update
    suspend fun update(comentario: ComentarioEntity)

    @Delete
    suspend fun delete(comentario: ComentarioEntity)

    @Query("DELETE FROM comentario")
    suspend fun deleteAll()

    @Query("SELECT IFNULL(max(idComentario),0) + 1 FROM comentario")
    suspend fun getNextId(): Int?
}