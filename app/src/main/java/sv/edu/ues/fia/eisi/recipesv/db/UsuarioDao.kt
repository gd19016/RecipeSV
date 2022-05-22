package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsuarioDao {
    @Query("SELECT nombre FROM rol")
    suspend fun getRolForSpinner(): Array<String>

    @Query("SELECT * FROM usuario")
    fun getAll():LiveData<List<UsuarioEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usuario: UsuarioEntity)

    @Update
    suspend fun update(usuario: UsuarioEntity)

    @Delete
    suspend fun delete(usuario: UsuarioEntity)

    @Query("DELETE FROM usuario")
    suspend fun deleteAll()

    @Query("SELECT * FROM usuario WHERE email = :pEmail and password = :pPassword")
    suspend fun getUsuario(pEmail: String, pPassword: String): UsuarioEntity?
}
