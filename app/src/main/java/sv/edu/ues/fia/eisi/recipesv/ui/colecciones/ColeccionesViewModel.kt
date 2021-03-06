package sv.edu.ues.fia.eisi.recipesv.ui.colecciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sv.edu.ues.fia.eisi.recipesv.db.ColeccionEntity
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaRepository

class ColeccionesViewModel (private val repository: RegistroRecetaRepository) : ViewModel() {

    val colecciones: LiveData<List<ColeccionEntity>> = repository.colecciones
    var coleccionActual: ColeccionEntity? = null

    fun insert(coleccion: ColeccionEntity) = viewModelScope.launch {
        repository.insert(coleccion)
    }
    fun update(coleccion: ColeccionEntity) = viewModelScope.launch {
        repository.update(coleccion)
    }
    fun delete(coleccion: ColeccionEntity) = viewModelScope.launch {
        repository.delete(coleccion)
    }
}

class ColeccionesViewModelFactory(private val repository: RegistroRecetaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColeccionesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColeccionesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}