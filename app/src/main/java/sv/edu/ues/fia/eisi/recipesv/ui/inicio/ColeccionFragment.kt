package sv.edu.ues.fia.eisi.recipesv.ui.inicio

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.ColeccionRecetasEntity
import sv.edu.ues.fia.eisi.recipesv.db.UsuarioEntity
import sv.edu.ues.fia.eisi.recipesv.entity.Usuario
import sv.edu.ues.fia.eisi.recipesv.ui.colecciones.ColeccionesFragment

class ColeccionFragment : Fragment(), ColeccionListAdapter.OnColeccionClickListener {
    companion object {
        fun newInstance() = ColeccionesFragment()
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
        return inflater.inflate(R.layout.fragment_coleccion, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val application = activity?.application as RegistroRecetaApplication
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ColeccionListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.colecciones.observe(viewLifecycleOwner, Observer { colecciones ->
            colecciones?.let { adapter.submitList(it) }
        })

        val listColecciones: Array<String>? = viewModel.getColleccionesForSpinner()

        val spinnerColecciones: Spinner = view.findViewById(R.id.spinnerColeccion)

        if (listColecciones != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listColecciones)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinnerColecciones.adapter = spinnerArrayAdapter
        }

        val btnAgregar: Button = view.findViewById(R.id.btn_agregar)

        btnAgregar.setOnClickListener {
            if (spinnerColecciones.selectedItem.toString().isNullOrBlank()) {
                Toast.makeText(requireContext(),"Es necesario elegir una colección.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val elegido: String? = spinnerColecciones.selectedItem.toString()

            val idColeccion: Int? = elegido?.split("-", ignoreCase = true, limit = 0)?.first()
                ?.toInt()

            val usuarioLogueado: Usuario? = application.usuarioLogueado
            val recetaActual = viewModel.recetaActual

            if (usuarioLogueado != null) {
                if (idColeccion != null && recetaActual != null)
                    viewModel.insert(ColeccionRecetasEntity(recetaActual.idReceta, idColeccion))
                spinnerColecciones.setSelection(0)
                Toast.makeText(requireContext(),"Se ha agregado a colección.",Toast.LENGTH_LONG).show()
            }

            viewModel.colecciones.observe(viewLifecycleOwner, Observer { coleccions ->
                coleccions?.let { adapter.submitList(it) }
            })
        }
    }
    override fun onDeleteColeccionClicked(coleccion: ColeccionRecetasEntity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Estas seguro que deseas borrar de la coleccion con identificador: ${coleccion.idColeccion}?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, id ->
                viewModel.delete(coleccion)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}