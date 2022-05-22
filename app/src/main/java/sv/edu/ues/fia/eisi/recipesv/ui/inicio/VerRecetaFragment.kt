package sv.edu.ues.fia.eisi.recipesv.ui.inicio

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.FavoritoEntity
import sv.edu.ues.fia.eisi.recipesv.db.HistoricoEntity


class VerRecetaFragment : Fragment() {
    private lateinit var viewModel: InicioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = activity?.application as RegistroRecetaApplication
        viewModel = ViewModelProvider(requireActivity(),
            InicioViewModelFactory(application.repository)
        ).get(InicioViewModel::class.java)
        return inflater.inflate(R.layout.fragment_ver_receta, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val application = activity?.application as RegistroRecetaApplication
        var idReceta: Int? = null
        val nombre: TextView = view.findViewById(R.id.nombre_input)
        val descripcionReceta: TextView = view.findViewById(R.id.descripcion_input)
        val favoritoButton: ImageButton = view.findViewById(R.id.btn_favorito)
        val iniciarButton: ImageButton = view.findViewById(R.id.btn_iniciar_conteo)
        val viewVimer: TextView = view.findViewById(R.id.view_timer)

        val anteriorButton: ImageButton = view.findViewById(R.id.btn_anterior)
        val siguienteButton: ImageButton = view.findViewById(R.id.btn_siguiente)

        val tituloPaso: TextView = view.findViewById(R.id.titulo_paso)
        val descripcionPaso: TextView = view.findViewById(R.id.descripcion_paso)
        val minutosRecordatorio: EditText = view.findViewById(R.id.tiempo_input)

        var pasosReceta: List<String>? = null

        var numPasoActual: Int = 0
        var numPasosTotales: Int = 0

        val receta = viewModel.recetaActual

        if (receta != null) {
            idReceta = receta.idReceta
            nombre.setText(receta.nombre)
            descripcionReceta.setText(receta.descripcion)
            pasosReceta = receta.pasos.lines()

            if (application.usuarioLogueado != null) {
                viewModel.insert(HistoricoEntity(receta.idReceta, application.usuarioLogueado!!.email))
            }
        } else {
            nombre.setText("")
            descripcionReceta.setText("")
        }

        val pasosDepurados: List<String>? = pasosReceta?.filter { !it.isNullOrEmpty() }?.toList()

        if (pasosDepurados != null) {
            numPasosTotales = pasosDepurados.size

            if (numPasosTotales > 0) {
                numPasoActual = 1

                val indicePaso = numPasoActual - 1

                "Paso: $numPasoActual:".also { tituloPaso.text = it }
                descripcionPaso.text = pasosDepurados[indicePaso] ?: "Detalle del paso vacío."
            }
        } else {
            numPasosTotales = 0
            numPasoActual = 0
        }

        /*val viewModelSpinner: InicioViewModel = ViewModelProvider(requireActivity(),
            InicioViewModelFactory(application.repository)
        ).get(InicioViewModel::class.java)

        val listColecciones: Array<String>? = viewModelSpinner.getColleccionesForSpinner()

        val spinner: Spinner = view.findViewById(R.id.spinner)

        if (listColecciones != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listColecciones)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinner.adapter = spinnerArrayAdapter
        }*/

        anteriorButton.setOnClickListener{
            if (numPasosTotales == 0) {
                Toast.makeText(requireContext(), "No hay pasos para la receta.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (numPasoActual == 1) {
                Toast.makeText(requireContext(), "No hay pasos previos.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            numPasoActual -= 1

            val indicePaso = numPasoActual - 1

            "Paso: $numPasoActual:".also { tituloPaso.text = it }
            descripcionPaso.text = pasosDepurados?.get(indicePaso) ?: "Detalle del paso vacío."
        }

        siguienteButton.setOnClickListener{
            if (numPasosTotales == 0) {
                Toast.makeText(requireContext(), "No hay pasos para la receta.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (numPasoActual == numPasosTotales) {
                Toast.makeText(requireContext(), "No hay más pasos.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            numPasoActual += 1

            val indicePaso = numPasoActual - 1

            "Paso: $numPasoActual:".also { tituloPaso.text = it }
            descripcionPaso.text = pasosDepurados?.get(indicePaso) ?: "Detalle del paso vacío."
        }

        iniciarButton.setOnClickListener{
            if (minutosRecordatorio.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Debe establecer el tiempo en minutos.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            iniciarButton.isEnabled = false

            val segundosEspera: Long = minutosRecordatorio.text.toString().toLong() * 60 * 1000

            val timer = (object : CountDownTimer(segundosEspera, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    ((millisUntilFinished / 1000).toString() + " segundos para terminar.").also { viewVimer.text = it }
                }

                override fun onFinish() {
                    Toast.makeText(requireContext(), "¡Se terminó el tiempo!.", Toast.LENGTH_LONG).show()
                    val builder = NotificationCompat.Builder(requireContext(), "notif_recipe_sv")
                        .setSmallIcon(R.drawable.ic_button_favorito_lleno)
                        .setContentTitle("Recipe SV")
                        .setContentText("Se ha completado el tiempo de su recordatorio")
                        .setStyle(
                            NotificationCompat.BigTextStyle()
                                .bigText("Regrese a la aplicación para seguir con su receta")
                        )
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    val manager = NotificationManagerCompat.from(requireContext())
                    manager.notify(0, builder.build())
                    iniciarButton.isEnabled = true
                }
            }).apply {
                start()
            }
        }

        favoritoButton.setOnClickListener{
            if (idReceta != null) {
                //val application = activity?.application as RegistroRecetaApplication
                val viewModelFavorito: InicioViewModel = ViewModelProvider(requireActivity(),
                    InicioViewModelFactory(application.repository)
                    ).get(InicioViewModel::class.java)

                val favoritoEntity: FavoritoEntity? = viewModelFavorito.getFavorito(idReceta, 0)

                if (favoritoEntity != null) {
                    viewModelFavorito.delete(
                        FavoritoEntity(
                            0,
                            idReceta
                        )
                    )
                    favoritoButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_button_favorito_borde))
                } else {
                    viewModelFavorito.insert(
                        FavoritoEntity(
                            0,
                            idReceta
                        )
                    )
                    favoritoButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_button_favorito_lleno))
                }
            }
        }

        val comentariosButton: ImageButton = view.findViewById(R.id.btn_comentarios)

        comentariosButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_inicio_to_nav_ver_comentarios)
        }

        val coleccionesButton: ImageButton = view.findViewById(R.id.btn_colecciones)

        coleccionesButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_inicio_to_nav_ver_colecciones)
        }
    }
}