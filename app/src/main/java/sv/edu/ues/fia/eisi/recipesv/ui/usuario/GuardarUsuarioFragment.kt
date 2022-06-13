package sv.edu.ues.fia.eisi.recipesv.ui.usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import sv.edu.ues.fia.eisi.recipesv.MainActivity
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.db.UsuarioEntity
import sv.edu.ues.fia.eisi.recipesv.ui.inicio.InicioViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.inicio.InicioViewModelFactory
import java.lang.Exception

class GuardarUsuarioFragment : Fragment() {

    private lateinit var viewModel: UsuarioViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            UsuarioViewModelFactory(application.repository)).get(UsuarioViewModel::class.java)
        return inflater.inflate(R.layout.fragment_guardar_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val correo: EditText = view.findViewById(R.id.email_input)
        val pass: EditText = view.findViewById(R.id.contrasena_input)
        val nombre: EditText = view.findViewById(R.id.nombre_input)
        val idRol: Spinner = view.findViewById(R.id.Rol_spinner)
        val guardarButton: Button = view.findViewById(R.id.guardar_usuario)

        val application = activity?.application as RegistroRecetaApplication
        val viewModelSpinner: UsuarioViewModel = ViewModelProvider(requireActivity(),
            UsuarioViewModelFactory(application.repository)
        ).get(UsuarioViewModel::class.java)

        val opcionRol: Array<String>? = viewModelSpinner.getRolForSpinner()

        if (opcionRol != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, opcionRol)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            idRol.adapter = spinnerArrayAdapter
        }

        val usuario = viewModel.usuarioActual

        if (usuario != null) {
            correo.isEnabled = false
            correo.setText(usuario.email)
            pass.setText(usuario.password)
            nombre.setText(usuario.nombre)
        } else {
            correo.setText("")
            pass.setText("")
            nombre.setText("")

        }

        guardarButton.setOnClickListener{
            if (correo.text.isNullOrBlank() ||
                pass.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank()) {
                Toast.makeText(context, "Todos los campos son requeridos",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (usuario != null) {
                Fuel.get(
                    "/usuarios/ws_update_usuarios.php",
                    listOf(
                        "email" to correo.text,
                        "password" to pass.text,
                        "nombre" to nombre.text,
                        "idRol" to idRol.selectedItem
                    )
                ).responseJson { _, _, result ->
                    requireActivity().runOnUiThread {
                        when (result) {
                            is Result.Failure -> {
                                Toast.makeText(context, result.getException().toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                            is Result.Success -> {
                                try {
                                    val data = result.get().obj()
                                    if (data.has("resultado") && data.getString("resultado") == "1") {
                                        Toast.makeText(context, "Registro actualizado",
                                            Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Registro no se pudo actualizar",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    findNavController().navigateUp()
                                } catch (e: Exception) {
                                    Toast.makeText(context, result.get().content,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                /*viewModel.update(
                    UsuarioEntity(
                        correo.text.toString(),
                        pass.text.toString(),
                        nombre.text.toString(),
                        idRol.toString()
                    )
                )*/
            } else {
                Fuel.get(
                    "/usuarios/ws_insertar_usuario.php",
                    listOf(
                        "email" to correo.text,
                        "password" to pass.text,
                        "nombre" to nombre.text,
                        "idRol" to idRol.selectedItem
                    )
                ).responseJson { _, _, result ->
                    requireActivity().runOnUiThread {
                        when (result) {
                            is Result.Failure -> {
                                Toast.makeText(context, result.getException().toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                            is Result.Success -> {
                                try {
                                    val data = result.get().obj()
                                    if (data.has("resultado") && data.getString("resultado") == "1") {
                                        Toast.makeText(context, "Registro insertado",
                                            Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Registro no se pudo insertar",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    findNavController().navigateUp()
                                } catch (e: Exception) {
                                    Toast.makeText(context, result.get().content,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}