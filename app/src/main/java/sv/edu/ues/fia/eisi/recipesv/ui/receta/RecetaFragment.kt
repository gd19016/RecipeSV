package sv.edu.ues.fia.eisi.recipesv.ui.receta

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity

class RecetaFragment : Fragment(), RecetaListAdapter.OnRecetaClickListener {
    companion object {
        fun newInstance() = RecetaFragment()
    }
    private lateinit var viewModel: RecetaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            RecetaViewModelFactory(application.repository)).get(RecetaViewModel::class.java)
        return inflater.inflate(R.layout.receta_fragment, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = RecetaListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.recetas.observe(viewLifecycleOwner, Observer { recetas ->
            recetas?.let { adapter.submitList(it) }
        })
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            viewModel.recetaActual = null
            findNavController().navigate(R.id.action_nav_receta_to_nav_guardar_receta)
        }
    }
    override fun onEditRecetaClicked(receta: RecetaEntity) {
        viewModel.recetaActual = receta
        findNavController().navigate(R.id.action_nav_receta_to_nav_guardar_receta)
    }
    override fun onDeleteRecetaClicked(receta: RecetaEntity) {
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
    }
}