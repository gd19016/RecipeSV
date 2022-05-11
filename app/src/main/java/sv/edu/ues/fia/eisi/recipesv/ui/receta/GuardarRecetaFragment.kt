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
        val guardarButton: Button = view.findViewById(R.id.guardar_receta)

        val receta = viewModel.recetaActual

        if (receta != null) {
            idReceta.isEnabled = false
            idReceta.setText(receta.idReceta.toString())
            nombre.setText(receta.nombre)
        } else {
            idReceta.setText("0")
            nombre.setText("")
        }

        guardarButton.setOnClickListener{
            if (idReceta.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank()) {
                Toast.makeText(context, "Todos los campos son requeridos",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (receta != null) {
                viewModel.update(
                    RecetaEntity(
                        receta.idReceta,
                        nombre.text.toString()
                    )
                )
            } else {
                viewModel.insert(
                    RecetaEntity(
                        idReceta.text.toString().toInt(),
                        nombre.text.toString()
                    )
                )
            }
            findNavController().navigateUp()
        }
    }
}