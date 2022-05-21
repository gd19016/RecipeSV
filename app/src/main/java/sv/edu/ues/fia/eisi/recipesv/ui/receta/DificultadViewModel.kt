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

class DificultadViewModel (private val repository: RegistroRecetaRepository) : ViewModel() {
    val recetas: LiveData<List<RecetaEntity>> = repository.recetas
    var recetaActual: RecetaEntity? = null

    val dificultades: LiveData<List<DificultadEntity>> = repository.dificultades
    var dificultadActual: DificultadEntity? = null
    var dificultadConsultada: DificultadEntity? = null

    fun insert(dificultad: DificultadEntity) = viewModelScope.launch {
        repository.insert(dificultad)
    }
    fun update(dificultad: DificultadEntity) = viewModelScope.launch {
        repository.update(dificultad)
    }
    fun delete(dificultad: DificultadEntity) = viewModelScope.launch {
        repository.delete(dificultad)
    }

    fun getDificultadForSpinner(): Array<String>? {
        var dificultadMostrar: Array<String>?
        runBlocking {
            val resultado = async { repository.getDificultadForSpinner() }
            runBlocking{
                dificultadMostrar = resultado.await()
            }
        }
        return dificultadMostrar
    }
}

class DificultadViewModelFactory(private val repository: RegistroRecetaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DificultadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DificultadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}