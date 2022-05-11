package sv.edu.ues.fia.eisi.recipesv.ui.receta

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaRepository

class RecetaViewModel(private val repository: RegistroRecetaRepository) : ViewModel() {
    val recetas: LiveData<List<RecetaEntity>> = repository.recetas
    var recetaActual: RecetaEntity? = null
    fun insert(receta: RecetaEntity) = viewModelScope.launch {
        repository.insert(receta)
    }
    fun update(receta: RecetaEntity) = viewModelScope.launch {
        repository.update(receta)
    }
    fun delete(receta: RecetaEntity) = viewModelScope.launch {
        repository.delete(receta)
    }
}

class RecetaViewModelFactory(private val repository: RegistroRecetaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecetaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}