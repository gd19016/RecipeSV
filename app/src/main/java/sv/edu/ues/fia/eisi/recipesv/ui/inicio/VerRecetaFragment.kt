package sv.edu.ues.fia.eisi.recipesv.ui.inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.FavoritoEntity
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity
import sv.edu.ues.fia.eisi.recipesv.ui.receta.RecetaViewModel
import sv.edu.ues.fia.eisi.recipesv.ui.receta.RecetaViewModelFactory

class VerRecetaFragment : Fragment() {
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
            RecetaViewModelFactory(application.repository)
        ).get(RecetaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_ver_receta, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idReceta: EditText = view.findViewById(R.id.id_receta_input)
        val nombre: EditText = view.findViewById(R.id.nombre_input)
        val favoritoButton: ImageButton = view.findViewById(R.id.btn_favorito)

        val receta = viewModel.recetaActual

        if (receta != null) {
            idReceta.isEnabled = false
            idReceta.setText(receta.idReceta.toString())
            nombre.setText(receta.nombre)
        } else {
            idReceta.setText("0")
            nombre.setText("")
        }

        favoritoButton.setOnClickListener{
            if (idReceta.text != null) {
                val application = activity?.application as RegistroRecetaApplication
                val viewModelFavorito: InicioViewModel = ViewModelProvider(requireActivity(),
                    InicioViewModelFactory(application.repository)
                    ).get(InicioViewModel::class.java)

                val favoritoEntity: FavoritoEntity? = viewModelFavorito.getFavorito(idReceta.text.toString().toInt(), 0)

                if (favoritoEntity != null) {
                    viewModelFavorito.delete(
                        FavoritoEntity(
                            0,
                            idReceta.text.toString().toInt()
                        )
                    )
                    favoritoButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_button_favorito_borde))
                } else {
                    viewModelFavorito.insert(
                        FavoritoEntity(
                            0,
                            idReceta.text.toString().toInt(),
                        )
                    )
                    favoritoButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_button_favorito_lleno))
                }
            }
        }
    }
}