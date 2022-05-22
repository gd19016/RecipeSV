package sv.edu.ues.fia.eisi.recipesv.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecetaDao {
    @Query("SELECT * FROM receta")
    fun getAll(): LiveData<List<RecetaEntity>>

    @Query("SELECT * " +
            "FROM receta rec " +
            "where :pEmail is not null" +
            " and (:pHistorica = 'false' or (:pHistorica = 'true' and exists (select null from historico hist where hist.id_receta = rec.id_receta)))" +
            " and (:pFavorita = 'false' or (:pFavorita = 'true' and exists (select null from favorito fav where fav.idReceta = rec.id_receta)))")
    suspend fun getAll(pEmail: String?, pHistorica: String?, pFavorita: String?): List<RecetaEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(receta: RecetaEntity)

    @Update
    suspend fun update(receta: RecetaEntity)

    @Delete
    suspend fun delete(receta: RecetaEntity)

    @Query("DELETE FROM receta")
    suspend fun deleteAll()
}