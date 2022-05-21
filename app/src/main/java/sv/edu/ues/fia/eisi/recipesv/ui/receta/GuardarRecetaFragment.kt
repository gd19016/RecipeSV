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
        val dificultad: Spinner = view.findViewById(R.id.spinnerDificultad)
        val tipo: Spinner = view.findViewById(R.id.spinnerTipo)
        val guardarButton: Button = view.findViewById(R.id.guardar_receta)

        val receta = viewModel.recetaActual

        if (receta != null) {
            idReceta.isEnabled = false
            idReceta.setText(receta.idReceta.toString())
            nombre.setText(receta.nombre)
            descripcion.setText(receta.descripcion)
            pasos.setText(receta.pasos)
        } else {
            idReceta.setText("0")
            nombre.setText("")
            descripcion.setText("")
            pasos.setText("")
        }

        //spinner Dificultad
        val application = activity?.application as RegistroRecetaApplication
        val viewModelSpinner: DificultadViewModel = ViewModelProvider(requireActivity(),
            DificultadViewModelFactory(application.repository)
        ).get(DificultadViewModel::class.java)

        val listDificultad: Array<String>? = viewModelSpinner.getDificultadForSpinner()

        val spinner: Spinner = view.findViewById(R.id.spinnerDificultad)

        if (listDificultad != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listDificultad)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinner.adapter = spinnerArrayAdapter
        } //Fin Spinner Dificultad

        //Spinner Tipo
        val applicationTipo = activity?.application as RegistroRecetaApplication
        val viewModelSpinnerTipo: TipoViewModel = ViewModelProvider(requireActivity(),
            TipoViewModelFactory(applicationTipo.repository)
        ).get(TipoViewModel::class.java)

        val listTipo: Array<String>? = viewModelSpinnerTipo.getTipoForSpinner()

        val spinnerTipo: Spinner = view.findViewById(R.id.spinnerTipo)

        if (listTipo != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listTipo)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinnerTipo.adapter = spinnerArrayAdapter
        } //Fin Spinner Tipo

        guardarButton.setOnClickListener{
            if (idReceta.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank()||
                descripcion.text.isNullOrBlank()||
                pasos.text.isNullOrBlank()) {
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
                        dificultad.selectedItem.toString(),
                        tipo.selectedItem.toString()
                    )
                )
            } else {
                viewModel.insert(
                    RecetaEntity(
                        idReceta.text.toString().toInt(),
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        pasos.text.toString(),
                        dificultad.selectedItem.toString(),
                        tipo.selectedItem.toString()
                    )
                )
            }
            findNavController().navigateUp()
        }
    }
}