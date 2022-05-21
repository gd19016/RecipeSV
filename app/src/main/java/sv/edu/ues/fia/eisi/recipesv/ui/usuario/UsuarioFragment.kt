package sv.edu.ues.fia.eisi.recipesv.ui.usuario

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
import sv.edu.ues.fia.eisi.recipesv.db.UsuarioEntity

class UsuarioFragment : Fragment(), UsuarioListAdapter.OnUsuarioClickListener {

    companion object {
        fun newInstance() = UsuarioFragment()
    }

    private lateinit var viewModel: UsuarioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            UsuarioViewModelFactory(application.repository)).get(UsuarioViewModel::class.java)
        return inflater.inflate(R.layout.fragment_usuario, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UsuarioListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.usuarios.observe(viewLifecycleOwner, Observer { usuarios -> usuarios?.let { adapter.submitList(it) }
        })
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            viewModel.usuarioActual = null
            findNavController().navigate(R.id.action_nav_usuario_to_nav_guardar_usuario)
        }
    }
    override fun onEditUsuarioClicked(usuario: UsuarioEntity) {
        viewModel.usuarioActual = usuario
        findNavController().navigate(R.id.action_nav_usuario_to_nav_guardar_usuario)
    }
    override fun onDeleteUsuarioClicked(usuario: UsuarioEntity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Estas seguro que deseas borrar el usuario con correo: ${usuario.email}?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, id -> viewModel.delete(usuario)
            }
            .setNegativeButton("No") { dialog, id -> dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}