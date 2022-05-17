package sv.edu.ues.fia.eisi.recipesv.ui.colecciones

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sv.edu.ues.fia.eisi.recipesv.R
import android.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.ColeccionesEntity

class ColeccionesFragment : Fragment(), ColeccionesListAdapter.OnColeccionesClickListener {

    companion object {
        fun newInstance() = ColeccionesFragment()
    }

    private lateinit var viewModel: ColeccionesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            ColeccionesViewModelFactory(application.repository)
        ).get(ColeccionesViewModel::class.java)
        return inflater.inflate(R.layout.colecciones_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ColeccionesListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.colecciones.observe(viewLifecycleOwner, Observer { colecciones ->
            colecciones?.let { adapter.submitList(it) }
        })
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            viewModel.coleccionActual = null
            findNavController().navigate(R.id.action_nav_coleccion_to_nav_guardar_coleccion)
        }
    }

    override fun onEditColeccionesClicked(coleccion: ColeccionesEntity) {
        viewModel.coleccionActual = coleccion
        findNavController().navigate(R.id.action_nav_coleccion_to_nav_guardar_coleccion)
    }

    override fun onDeleteColeccionesClicked(coleccion: ColeccionesEntity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Estas seguro que deseas borrar la coleccion con identificador: ${coleccion.idColeccion}?")
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