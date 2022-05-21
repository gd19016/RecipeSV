package sv.edu.ues.fia.eisi.recipesv.ui.ingrediente

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
import sv.edu.ues.fia.eisi.recipesv.db.IngredienteEntity
import sv.edu.ues.fia.eisi.recipesv.ui.ingrediente.IngredienteListAdapter
import sv.edu.ues.fia.eisi.recipesv.ui.ingrediente.IngredienteViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.ingrediente.IngredienteViewModelFactory

class IngredienteFragment : Fragment(), IngredienteListAdapter.OnIngredienteClickListener {

    companion object {
        fun newInstance() = IngredienteFragment()
    }

    private lateinit var viewModel: IngredienteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            IngredienteViewModelFactory(application.repository)
        ).get(IngredienteViewModel::class.java)
        return inflater.inflate(R.layout.ingrediente_fragment, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = IngredienteListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.ingredientes.observe(viewLifecycleOwner, Observer { ingredientes -> ingredientes?.let { adapter.submitList(it) }
        })
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            viewModel.ingredienteActual = null
            findNavController().navigate(R.id.action_nav_ingrediente_to_nav_guardar_ingrediente)
        }
    }
    override fun onEditIngredienteClicked(ingrediente: IngredienteEntity) {
        viewModel.ingredienteActual = ingrediente
        findNavController().navigate(R.id.action_nav_ingrediente_to_nav_guardar_ingrediente)
    }
    override fun onDeleteIngredienteClicked(ingrediente: IngredienteEntity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Estas seguro que deseas borrar el ingrediente con identificador: ${ingrediente.idIngrediente}?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, id -> viewModel.delete(ingrediente)
            }
            .setNegativeButton("No") { dialog, id -> dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}