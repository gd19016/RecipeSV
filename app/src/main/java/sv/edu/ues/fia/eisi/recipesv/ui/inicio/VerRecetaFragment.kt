package sv.edu.ues.fia.eisi.recipesv.ui.inicio

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
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
        val iniciarButton: ImageButton = view.findViewById(R.id.btn_iniciar_conteo)
        val viewVimer: TextView = view.findViewById(R.id.view_timer)

        val receta = viewModel.recetaActual

        if (receta != null) {
            idReceta.isEnabled = false
            idReceta.setText(receta.idReceta.toString())
            nombre.setText(receta.nombre)
        } else {
            idReceta.setText("0")
            nombre.setText("")
        }

        iniciarButton.setOnClickListener{
            val timer = (object : CountDownTimer(20000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    viewVimer.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    Toast.makeText(requireContext(), "Finished.", Toast.LENGTH_LONG).show()
                    val builder = NotificationCompat.Builder(requireContext(), "notif_recipe_sv")
                        .setSmallIcon(R.drawable.ic_button_favorito_lleno)
                        .setContentTitle("My notification")
                        .setContentText("Much longer text that cannot fit one line...")
                        .setStyle(
                            NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line...")
                        )
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    val manager = NotificationManagerCompat.from(requireContext())
                    manager.notify(0, builder.build())
                }
            }).apply {
                start()
            }
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