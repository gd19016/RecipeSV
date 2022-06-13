package sv.edu.ues.fia.eisi.recipesv.ui.inicio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sv.edu.ues.fia.eisi.recipesv.db.*
import sv.edu.ues.fia.eisi.recipesv.entity.RecetaEntidad
import sv.edu.ues.fia.eisi.recipesv.ui.colecciones.ColeccionesFragment

class InicioViewModel(private val repository: RegistroRecetaRepository) : ViewModel() {
    //val recetas: LiveData<List<RecetaEntidad>> = repository.recetas
    val comentarios: LiveData<List<ComentarioEntity>> = repository.comentarios
    val colecciones: LiveData<List<ColeccionRecetasEntity>> = repository.coleccionReceta
    var recetaActual: RecetaEntidad? = null
    val favoritos: LiveData<List<FavoritoEntity>> = repository.favoritos
    var favoritoActual: FavoritoEntity? = null
    var favoritoConsultado: FavoritoEntity? = null
    fun insert(favorito: FavoritoEntity) = viewModelScope.launch {
        repository.insert(favorito)
    }
    fun insert(comentario: ComentarioEntity) = viewModelScope.launch {
        repository.insert(comentario)
    }
    fun insert(coleccion: ColeccionRecetasEntity) = viewModelScope.launch {
        repository.insert(coleccion)
    }
    fun update(favorito: FavoritoEntity) = viewModelScope.launch {
        repository.update(favorito)
    }
    fun delete(favorito: FavoritoEntity) = viewModelScope.launch {
        repository.delete(favorito)
    }
    fun delete(comentario: ComentarioEntity) = viewModelScope.launch {
        repository.delete(comentario)
    }
    fun delete(coleccion: ColeccionRecetasEntity) = viewModelScope.launch {
        repository.delete(coleccion)
    }
    fun getFavorito(pIdReceta: Int, pIdUsuario: Int): FavoritoEntity? {
        var favoritoConsultado: FavoritoEntity?
        runBlocking {
            val resultado = async { repository.getFavorito(pIdReceta, pIdUsuario) }
            runBlocking{
                favoritoConsultado = resultado.await()
            }
        }
        return favoritoConsultado
    }
    fun getNextCommentId(): Int? {
        var nextId: Int?
        runBlocking {
            val resultado = async { repository.getNextCommentId() }
            runBlocking{
                nextId = resultado.await()
            }
        }
        return nextId
    }
    fun getColleccionesForSpinner(): Array<String>? {
        var colleccionesMostrar: Array<String>?
        runBlocking {
            val resultado = async { repository.getColeccionesForSpinner() }
            runBlocking{
                colleccionesMostrar = resultado.await()
            }
        }
        return colleccionesMostrar
    }
    fun insert(historicoEntity: HistoricoEntity) = viewModelScope.launch {
        repository.insert(historicoEntity)
    }
    /*suspend fun getAll(pEmail: String?, pHistorica: String?, pFavorita: String?): List<RecetaEntidad> {
        //return repository.getAll(pEmail, pHistorica, pFavorita)
        return null
    }*/
}

class InicioViewModelFactory(private val repository: RegistroRecetaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InicioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}