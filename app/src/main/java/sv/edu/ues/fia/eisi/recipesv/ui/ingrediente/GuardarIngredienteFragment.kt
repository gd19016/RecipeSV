package sv.edu.ues.fia.eisi.recipesv.ui.ingrediente
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
import sv.edu.ues.fia.eisi.recipesv.db.IngredienteEntity
import sv.edu.ues.fia.eisi.recipesv.ui.ingrediente.IngredienteViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.ingrediente.IngredienteViewModelFactory

class GuardarIngredienteFragment : Fragment() {
    private lateinit var viewModel: IngredienteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            IngredienteViewModelFactory(application.repository)
        ).get(IngredienteViewModel::class.java)
        return inflater.inflate(R.layout.fragment_guardar_ingrediente, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idIngrediente: EditText = view.findViewById(R.id.id_ingrediente_input)
        val nombre: EditText = view.findViewById(R.id.nombre_input)
        val guardarButton: Button = view.findViewById(R.id.guardar_ingrediente)

        val ingrediente = viewModel.ingredienteActual

        if (ingrediente != null) {
            idIngrediente.isEnabled = false
            idIngrediente.setText(ingrediente.idIngrediente.toString())
            nombre.setText(ingrediente.nombre)

        } else {
            idIngrediente.setText("0")
            nombre.setText("")

        }

        guardarButton.setOnClickListener{
            if (idIngrediente.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank()) {
                Toast.makeText(context, "Todos los campos son requeridos",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (ingrediente != null) {
                viewModel.update(
                    IngredienteEntity(
                        ingrediente.idIngrediente,
                        nombre.text.toString()
                    )
                )
            } else {
                viewModel.insert(
                    IngredienteEntity(
                        idIngrediente.text.toString().toInt(),
                        nombre.text.toString(),
                    )
                )
            }
            findNavController().navigateUp()
        }
    }
}