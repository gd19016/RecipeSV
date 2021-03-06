package sv.edu.ues.fia.eisi.recipesv.ui.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sv.edu.ues.fia.eisi.recipesv.db.RegistroRecetaRepository
import sv.edu.ues.fia.eisi.recipesv.entity.Usuario

class UsuarioViewModel(private val repository: RegistroRecetaRepository) : ViewModel() {

    //val usuarios: LiveData<List<Usuario>> = repository.usuarios
    var usuarioActual: Usuario? = null

    fun insert(usuario: Usuario) = viewModelScope.launch {
        //repository.insert(usuario)
    }

    fun update(usuario: Usuario) = viewModelScope.launch {
        //repository.update(usuario)
    }

    fun delete(usuario: Usuario) = viewModelScope.launch {
        //repository.delete(usuario)
    }

    fun getRolForSpinner(): Array<String>? {
        var RolesMostrar: Array<String>?
        runBlocking {
            val resultado = async { repository.ObtenerRolForSpinner()}
            runBlocking{
                RolesMostrar = resultado.await()
            }
        }
        return RolesMostrar
    }
}
class UsuarioViewModelFactory(private val repository: RegistroRecetaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
