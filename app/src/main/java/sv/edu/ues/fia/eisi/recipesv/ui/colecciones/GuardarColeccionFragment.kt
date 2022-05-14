package sv.edu.ues.fia.eisi.recipesv.ui.colecciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.ColeccionesEntity

class GuardarColeccionFragment : Fragment() {

    private lateinit var viewModel: ColeccionesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            ColeccionesViewModelFactory(application.repository)
        ).get(ColeccionesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_guardar_coleccion, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val idColeccion: EditText = view.findViewById(R.id.idColeccion_input)
        val nombre: EditText = view.findViewById(R.id.nombreColeccion_input)
        val descripcion: EditText = view.findViewById(R.id.descripcionColeccion_input)
        val idUsuarioC: EditText = view.findViewById(R.id.idUserC_input)
        val guardarButton: Button = view.findViewById(R.id.guardar_coleccion)

        val coleccion = viewModel.coleccionActual

        if (coleccion != null) {
            idColeccion.isEnabled = false
            idColeccion.setText(coleccion.idColeccion.toString())
            nombre.setText(coleccion.nombre)
            descripcion.setText(coleccion.descripcion)
            idUsuarioC.setText(coleccion.idUsuario.toString())

        } else {
            idColeccion.setText("0")
            nombre.setText("")
            descripcion.setText("")
            idUsuarioC.setText("0")
        }

        guardarButton.setOnClickListener{
            if (idColeccion.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank() ||
                    descripcion.text.isNullOrBlank() ||
                idUsuarioC.text.isNullOrBlank()) {
                Toast.makeText(context, "Todos los campos son requeridos",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (coleccion != null) {
                viewModel.update(
                    ColeccionesEntity(
                        coleccion.idColeccion,
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        coleccion.idUsuario
                    )
                )
            } else {
                viewModel.insert(
                    ColeccionesEntity(
                        idColeccion.text.toString().toInt(),
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        idUsuarioC.text.toString().toInt()
                    )
                )
            }
            findNavController().navigateUp()
        }
    }
}