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
import sv.edu.ues.fia.eisi.recipesv.db.ComentarioEntity
import sv.edu.ues.fia.eisi.recipesv.db.HistoricoEntity
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity
import sv.edu.ues.fia.eisi.recipesv.db.UsuarioEntity
import sv.edu.ues.fia.eisi.recipesv.ui.receta.DificultadViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.receta.DificultadViewModelFactory
import sv.edu.ues.fia.eisi.recipesv.ui.receta.TipoViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.receta.TipoViewModelFactory

class ComentariosFragment : Fragment(), ComentarioListAdapter.OnComentarioClickListener {
    companion object {
        fun newInstance() = ComentariosFragment()
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
        return inflater.inflate(R.layout.fragment_comentarios, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val application = activity?.application as RegistroRecetaApplication
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ComentarioListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.comentarios.observe(viewLifecycleOwner, Observer { comentarios ->
            comentarios?.let { adapter.submitList(it) }
        })

        val comentarioInput: EditText = view.findViewById(R.id.comentario_input)
        val btnAgregar: Button = view.findViewById(R.id.btn_agregar)

        btnAgregar.setOnClickListener {
            if (comentarioInput.text.isNullOrBlank()) {
                Toast.makeText(requireContext(),"Es necesario redactar un comentario.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var nextCommentId: Int? = viewModel.getNextCommentId()

            if (nextCommentId == null)
                nextCommentId = 1

            val usuarioLogueado: UsuarioEntity? = application.usuarioLogueado

            if (usuarioLogueado != null) {
                viewModel.insert(ComentarioEntity(nextCommentId,usuarioLogueado.email, comentarioInput.text.toString()))
                comentarioInput.setText("")
                Toast.makeText(requireContext(),"Comentario guardado.",Toast.LENGTH_LONG).show()
            }

            viewModel.comentarios.observe(viewLifecycleOwner, Observer { comentarios ->
                comentarios?.let { adapter.submitList(it) }
            })
        }
    }
    override fun onDeleteComentarioClicked(comentario: ComentarioEntity) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Estas seguro que deseas borrar el comentario con identificador: ${comentario.idComentario}?")
            .setCancelable(false)
            .setPositiveButton("Si") { dialog, id ->
                viewModel.delete(comentario)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}