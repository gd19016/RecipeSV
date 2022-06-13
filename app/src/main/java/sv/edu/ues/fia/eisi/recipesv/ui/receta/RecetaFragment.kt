package sv.edu.ues.fia.eisi.recipesv.ui.receta

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.entity.RecetaEntidad

class RecetaFragment : Fragment(), RecetaListAdapter.OnRecetaClickListener {
    companion object {
        fun newInstance() = RecetaFragment()
    }
    private lateinit var viewModel: RecetaViewModel
    var listaRecetas: ArrayList<RecetaEntidad> = ArrayList()
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

        var recetas : RecetaEntidad?=null
        var gson : Gson = Gson()

        Fuel.get(
            "/recetas/ws_query_all_receta.php"

        ).responseJson { _, _, result ->
            requireActivity().runOnUiThread {
                when (result) {
                    is Result.Failure -> {
                        //msjError = result.getException().toString();
                        //Toast.makeText(this@MainActivity, result.getException().toString(), Toast.LENGTH_LONG).show()
                    }
                    is Result.Success -> {
                        val data = result.get().array()
                        if (data.length() > 0) {
                            if (!listaRecetas.isEmpty())
                                listaRecetas.clear()

                            for (i in 0..data.length()-1) {
                                var jsonObject: String? = data.getJSONObject(i).toString()

                                if (jsonObject != null) {
                                    recetas = gson.fromJson(jsonObject, RecetaEntidad::class.java)
                                    recetas?.let { listaRecetas.add(it) }
                                }
                            }
                            if (!listaRecetas.isEmpty()) {
                                adapter.submitList(listaRecetas.toList())
                            }

                            //msjError = "Encontrado: " + data.getJSONObject(0).getString("nombre");
                            //Toast.makeText(this@MainActivity, "Encontrado: " + data.getJSONObject(0).getString("EMAIL"), Toast.LENGTH_LONG).show()
                        } else {
                            //msjError = "Usuario o contraseña incorrectos.";
                            //Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }
        /*viewModel.recetas.observe(viewLifecycleOwner, Observer { recetas ->
            recetas?.let { adapter.submitList(it) }
        })*/
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            viewModel.recetaActual = null
            findNavController().navigate(R.id.action_nav_receta_to_nav_guardar_receta)
        }
    }
    override fun onEditRecetaClicked(receta: RecetaEntidad) {
        viewModel.recetaActual = receta
        findNavController().navigate(R.id.action_nav_receta_to_nav_guardar_receta)
    }
    override fun onDeleteRecetaClicked(receta: RecetaEntidad) {
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