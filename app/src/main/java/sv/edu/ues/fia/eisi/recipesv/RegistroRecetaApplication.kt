package sv.edu.ues.fia.eisi.recipesv

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaDB
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaRepository
import sv.edu.ues.fia.eisi.recipesv.db.UsuarioEntity
import sv.edu.ues.fia.eisi.recipesv.entity.Usuario

class RegistroRecetaApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RegistroRecetaDB.getDatabase(this, applicationScope) }
    val repository by lazy { RegistroRecetaRepository(database) }
    var usuarioLogueado: Usuario? = null
}