package sv.edu.ues.fia.eisi.recipesv.ui.inicio

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.HistoricoEntity
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity
import sv.edu.ues.fia.eisi.recipesv.ui.receta.DificultadViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.receta.DificultadViewModelFactory
import sv.edu.ues.fia.eisi.recipesv.ui.receta.TipoViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.receta.TipoViewModelFactory

class InicioFragment : Fragment(), InicioListAdapter.OnInicioClickListener {
    companion object {
        fun newInstance() = InicioFragment()
    }
    private lateinit var viewModel: InicioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            InicioViewModelFactory(application.repository)
        ).get(InicioViewModel::class.java)
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val application = activity?.application as RegistroRecetaApplication
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = InicioListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.recetas.observe(viewLifecycleOwner, Observer { recetas ->
            recetas?.let { adapter.submitList(it) }
        })

        val chkFavorita: CheckBox = view.findViewById(R.id.chk_favorita)
        val chkHistorica: CheckBox = view.findViewById(R.id.chk_vista)

        /*val viewModelSpinner: InicioViewModel = ViewModelProvider(requireActivity(),
            InicioViewModelFactory(application.repository)
        ).get(InicioViewModel::class.java)*/

        val listColecciones: Array<String>? = viewModel.getColleccionesForSpinner()

        val spinnerColecciones: Spinner = view.findViewById(R.id.spinnerColeccion)

        if (listColecciones != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listColecciones)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinnerColecciones.adapter = spinnerArrayAdapter
        }

        //spinner Dificultad
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
        val viewModelSpinnerTipo: TipoViewModel = ViewModelProvider(requireActivity(),
            TipoViewModelFactory(application.repository)
        ).get(TipoViewModel::class.java)

        val listTipo: Array<String>? = viewModelSpinnerTipo.getTipoForSpinner()

        val spinnerTipo: Spinner = view.findViewById(R.id.spinnerTipo)

        if (listTipo != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listTipo)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinnerTipo.adapter = spinnerArrayAdapter
        } //Fin Spinner Tipo

        val buscarButton: Button = view.findViewById(R.id.btn_buscar)

        buscarButton.setOnClickListener {
            val buscarFavoritas: String = (chkFavorita.isChecked == true).toString()
            val buscarHistorica: String = (chkHistorica.isChecked == true).toString()
            var resultados: List<RecetaEntity>?
            runBlocking {
                val resultado = async { viewModel.getAll(application.usuarioLogueado?.email,buscarFavoritas,buscarHistorica) }
                runBlocking{
                    resultados = resultado.await()
                }
            }

            adapter.submitList(resultados)
        }

        /*val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            viewModel.recetaActual = null
            findNavController().navigate(R.id.action_nav_receta_to_nav_guardar_receta)
        }*/
    }
    override fun onPreviewInicioClicked(receta: RecetaEntity) {
        viewModel.recetaActual = receta
        findNavController().navigate(R.id.action_nav_inicio_to_nav_ver_receta)
    }
    /*override fun onEditInicioClicked(receta: RecetaEntity) {
        viewModel.recetaActual = receta
        findNavController().navigate(R.id.action_nav_receta_to_nav_guardar_receta)
    }
    override fun onDeleteInicioClicked(receta: RecetaEntity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Estas seguro que deseas borrar la receta con identificador: ${receta.idReceta}?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, id ->
                viewModel.delete(receta)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }*/
}