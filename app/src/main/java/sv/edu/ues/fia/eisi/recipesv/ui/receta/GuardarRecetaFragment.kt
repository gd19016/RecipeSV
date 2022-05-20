package sv.edu.ues.fia.eisi.recipesv.ui.receta

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
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity

class GuardarRecetaFragment : Fragment() {
    private lateinit var viewModel: RecetaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            RecetaViewModelFactory(application.repository)).get(RecetaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_guardar_receta, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idReceta: EditText = view.findViewById(R.id.id_receta_input)
        val nombre: EditText = view.findViewById(R.id.nombre_input)
        val descripcion: EditText = view.findViewById(R.id.descripcion_input)
        val pasos: EditText = view.findViewById(R.id.pasos_input)
        val tiempo: EditText = view.findViewById(R.id.tiempo_input)
        val guardarButton: Button = view.findViewById(R.id.guardar_receta)

        val receta = viewModel.recetaActual

        if (receta != null) {
            idReceta.isEnabled = false
            idReceta.setText(receta.idReceta.toString())
            nombre.setText(receta.nombre)
            descripcion.setText(receta.descripcion)
            pasos.setText(receta.pasos)
            tiempo.setText(receta.tiempo)
        } else {
            idReceta.setText("0")
            nombre.setText("")
            descripcion.setText("")
            pasos.setText("")
            tiempo.setText("")
        }

        guardarButton.setOnClickListener{
            if (idReceta.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank()||
                descripcion.text.isNullOrBlank()||
                pasos.text.isNullOrBlank()||
                tiempo.text.isNullOrBlank()) {
                Toast.makeText(context, "Todos los campos son requeridos",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (receta != null) {
                viewModel.update(
                    RecetaEntity(
                        receta.idReceta,
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        pasos.text.toString(),
                        tiempo.text.toString().toInt()
                    )
                )
            } else {
                viewModel.insert(
                    RecetaEntity(
                        idReceta.text.toString().toInt(),
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        pasos.text.toString(),
                        tiempo.text.toString().toInt()
                    )
                )
            }
            findNavController().navigateUp()
        }
    }
}