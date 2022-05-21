package sv.edu.ues.fia.eisi.recipesv.ui.receta

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sv.edu.ues.fia.eisi.recipesv.db.DificultadEntity
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaRepository
import sv.edu.ues.fia.eisi.recipesv.db.TipoEntity

class TipoViewModel (private val repository: RegistroRecetaRepository) : ViewModel() {

    val recetas: LiveData<List<RecetaEntity>> = repository.recetas
    var recetaActual: RecetaEntity? = null

    val tipos: LiveData<List<TipoEntity>> = repository.tipos
    var tipoActual: TipoEntity? = null
    var tipoConsultado: TipoEntity? = null

    fun insert(tipo: TipoEntity) = viewModelScope.launch {
        repository.insert(tipo)
    }
    fun update(tipo: TipoEntity) = viewModelScope.launch {
        repository.update(tipo)
    }
    fun delete(tipo: TipoEntity) = viewModelScope.launch {
        repository.delete(tipo)
    }

    fun getTipoForSpinner(): Array<String>? {
        var tipoMostrar: Array<String>?
        runBlocking {
            val resultado = async { repository.getTipoForSpinner() }
            runBlocking{
                tipoMostrar = resultado.await()
            }
        }
        return tipoMostrar
    }
}

class TipoViewModelFactory(private val repository: RegistroRecetaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TipoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TipoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}