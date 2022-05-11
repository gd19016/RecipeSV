package sv.edu.ues.fia.eisi.recipesv.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RegistroRecetaRepository(private val db: RegistroRecetaDB) {
    /***************************
     * Receta repository
     ***************************/
    val recetas: LiveData<List<RecetaEntity>> = db.recetaDao().getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(receta: RecetaEntity) {
        db.recetaDao().insert(receta)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(receta: RecetaEntity) {
        db.recetaDao().update(receta)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(receta: RecetaEntity) {
        db.recetaDao().delete(receta)
    }
}