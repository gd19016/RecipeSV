package sv.edu.ues.fia.eisi.recipesv.ui.receta

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.launch
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaRepository
import sv.edu.ues.fia.eisi.recipesv.entity.RecetaEntidad
import java.lang.Exception

class RecetaViewModel(private val repository: RegistroRecetaRepository) : ViewModel() {
    //val recetas: LiveData<List<RecetaEntidad>> = repository.recetas
    var recetaActual: RecetaEntidad? = null
    fun insert(receta: RecetaEntidad) = viewModelScope.launch {
        //repository.insert(receta)
    }
    fun update(receta: RecetaEntidad) = viewModelScope.launch {
        //repository.update(receta)
    }
    fun delete(receta: RecetaEntidad) = viewModelScope.launch {
        //repository.delete(receta)
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