package sv.edu.ues.fia.eisi.recipesv.ui.usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.db.UsuarioEntity

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
        val idUsuario: EditText = view.findViewById(R.id.usuarioId_input)
        val correo: EditText = view.findViewById(R.id.email_input)
        val pass: EditText = view.findViewById(R.id.contrasena_input)
        val nombre: EditText = view.findViewById(R.id.nombre_input)
        val idRol: RadioGroup = view.findViewById(R.id.Rol_radio_group)
        val guardarButton: Button = view.findViewById(R.id.guardar_usuario)

        val usuario = viewModel.usuarioActual

        if (usuario != null) {
            idUsuario.isEnabled = false
            idUsuario.setText(usuario.idUsuario.toString())
            correo.setText(usuario.email)
            pass.setText(usuario.password)
            nombre.setText(usuario.nombre)
            idRol.check(if(usuario.idRol == 'A') R.id.admin_radio else R.id.user_radio)
        } else {
            idUsuario.setText("0")
            correo.setText("")
            pass.setText("")
            nombre.setText("")
            idRol.check(R.id.admin_radio)
        }

        guardarButton.setOnClickListener{
            if (idUsuario.text.isNullOrBlank() ||
                correo.text.isNullOrBlank() ||
                pass.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank()) {
                Toast.makeText(context, "Todos los campos son requeridos",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (usuario != null) {
                viewModel.update(
                    UsuarioEntity(
                        usuario.idUsuario,
                        correo.text.toString(),
                        pass.text.toString(),
                        nombre.text.toString(),
                        if (idRol.checkedRadioButtonId == R.id.admin_radio) 'A' else 'U'
                    )
                )
            } else {
                viewModel.insert(
                    UsuarioEntity(
                        idUsuario.text.toString().toInt(),
                        correo.text.toString(),
                        pass.text.toString(),
                        nombre.text.toString(),
                        if (idRol.checkedRadioButtonId == R.id.admin_radio) 'A' else 'U'
                    )
                )
            }
            findNavController().navigateUp()
        }
    }
}