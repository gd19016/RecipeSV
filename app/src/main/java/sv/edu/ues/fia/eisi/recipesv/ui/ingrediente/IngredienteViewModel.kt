package sv.edu.ues.fia.eisi.recipesv.ui.ingrediente

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sv.edu.ues.fia.eisi.recipesv.db.IngredienteEntity
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaRepository

class IngredienteViewModel(private val repository: RegistroRecetaRepository) : ViewModel() {
    val ingredientes: LiveData<List<IngredienteEntity>> = repository.ingredientes
    var ingredienteActual: IngredienteEntity? = null
    fun insert(ingrediente: IngredienteEntity) = viewModelScope.launch {
        repository.insert(ingrediente)
    }
    fun update(ingrediente: IngredienteEntity) = viewModelScope.launch {
        repository.update(ingrediente)
    }
    fun delete(ingrediente: IngredienteEntity) = viewModelScope.launch {
        repository.delete(ingrediente)
    }
}

class IngredienteViewModelFactory(private val repository: RegistroRecetaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredienteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredienteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}