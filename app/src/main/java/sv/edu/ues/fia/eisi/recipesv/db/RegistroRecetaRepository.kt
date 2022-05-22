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

    suspend fun getAll(pEmail: String?, pHistorica: String?, pFavorita: String?): List<RecetaEntity> {
        return db.recetaDao().getAll(pEmail, pHistorica, pFavorita)
    }

    /***************************
     * Colecciones repository
     ***************************/
    val colecciones: LiveData<List<ColeccionesEntity>> = db.coleccionesDao().getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(coleccion: ColeccionesEntity) {
        db.coleccionesDao().insert(coleccion)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(coleccion: ColeccionesEntity) {
        db.coleccionesDao().update(coleccion)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(coleccion: ColeccionesEntity) {
        db.coleccionesDao().delete(coleccion)
    }

    suspend fun getColeccionesForSpinner(): Array<String> {
        return db.coleccionesDao().getAllForSpinner()
    }

    /***************************
     * Usuario repository
     ***************************/
    val usuarios: LiveData<List<UsuarioEntity>> = db.usuarioDao().getAll()
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

    suspend fun ObtenerRolForSpinner(): Array<String> {
        return db.usuarioDao().getRolForSpinner()
    }

    suspend fun getUsuario(pEmail: String, pPassword: String): UsuarioEntity? {
        return db.usuarioDao().getUsuario(pEmail, pPassword)
    }

    /***************************
     * Favorito repository
     ***************************/
    val favoritos: LiveData<List<FavoritoEntity>> = db.favoritoDao().getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(favorito: FavoritoEntity) {
        db.favoritoDao().insert(favorito)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(favorito: FavoritoEntity) {
        db.favoritoDao().update(favorito)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(favorito: FavoritoEntity) {
        db.favoritoDao().delete(favorito)
    }

    suspend fun getFavorito(pIdReceta: Int, pIdUsuario: Int): FavoritoEntity? {
        return db.favoritoDao().getFavorito(pIdReceta, pIdUsuario)
    }

    /***************************
     * Dificultad repository
     ***************************/

    val dificultades: LiveData<List<DificultadEntity>> = db.dificultadDao().getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dificultad: DificultadEntity) {
        db.dificultadDao().insert(dificultad)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(dificultad: DificultadEntity) {
        db.dificultadDao().update(dificultad)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(dificultad: DificultadEntity) {
        db.dificultadDao().delete(dificultad)
    }

    suspend fun getDificultadForSpinner(): Array<String> {
        return db.dificultadDao().getAllForSpinnerDificultad()
    }

    /***************************
     * Tipo repository
     ***************************/

    val tipos: LiveData<List<TipoEntity>> = db.tipoDao().getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(tipo: TipoEntity) {
        db.tipoDao().insert(tipo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(tipo: TipoEntity) {
        db.tipoDao().update(tipo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(tipo: TipoEntity) {
        db.tipoDao().delete(tipo)
    }

    suspend fun getTipoForSpinner(): Array<String> {
        return db.tipoDao().getAllForSpinnerTipo()
    }

    /***************************
     * Rol repository
     ***************************/

    val rol: LiveData<List<RolEntity>> = db.rolDao().getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(rol: RolEntity) {
        db.rolDao().insert(rol)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(rol: RolEntity) {
        db.rolDao().update(rol)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(rol: RolEntity) {
        db.rolDao().delete(rol)
    }

    /***************************
     * Ingrediente repository
     ***************************/

    val ingredientes: LiveData<List<IngredienteEntity>> = db.ingredienteDao().getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(ingrediente: IngredienteEntity) {
        db.ingredienteDao().insert(ingrediente)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(ingrediente: IngredienteEntity) {
        db.ingredienteDao().update(ingrediente)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(ingrediente: IngredienteEntity) {
        db.ingredienteDao().delete(ingrediente)
    }

    /***************************
     * Historico repository
     ***************************/

    val historicos: LiveData<List<HistoricoEntity>> = db.historicoDao().getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(historico: HistoricoEntity) {
        db.historicoDao().insert(historico)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(historico: HistoricoEntity) {
        db.historicoDao().update(historico)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(historico: HistoricoEntity) {
        db.historicoDao().delete(historico)
    }
}