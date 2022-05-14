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

    /***************************
     * Usuario repository
     ***************************/

    val usuarios: LiveData<List<UsuarioEntity>> = db.usuarioDao().getAll();
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(usuario: UsuarioEntity) {
        db.usuarioDao().insert(usuario)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(usuario: UsuarioEntity) {
        db.usuarioDao().update(usuario)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(usuario: UsuarioEntity) {
        db.usuarioDao().delete(usuario)
    }

}