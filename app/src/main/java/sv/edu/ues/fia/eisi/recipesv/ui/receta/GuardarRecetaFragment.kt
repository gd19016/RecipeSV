package sv.edu.ues.fia.eisi.recipesv.ui.receta

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity
import java.lang.Exception
import java.util.*

class GuardarRecetaFragment : Fragment() {

    private val SPEECH_CAPTURE = 111
    private lateinit var textView: TextView
    private lateinit var talkButton: ImageButton

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
            RecetaViewModelFactory(application.repository))
            .get(RecetaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_guardar_receta, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val idReceta: EditText = view.findViewById(R.id.id_receta_input)
        val nombre: EditText = view.findViewById(R.id.nombre_input)
        val descripcion: EditText = view.findViewById(R.id.descripcion_input)
        val pasos: EditText = view.findViewById(R.id.pasos_input)
        val dificultad: Spinner = view.findViewById(R.id.spinnerDificultad)
        val tipo: Spinner = view.findViewById(R.id.spinnerTipo)
        val tiempo: EditText = view.findViewById(R.id.tiempo_input)
        val guardarButton: Button = view.findViewById(R.id.guardar_receta)
        textView = view.findViewById(R.id.descripcion_input)
        talkButton = view.findViewById(R.id.talkBtn)

        val receta = viewModel.recetaActual

        if (receta != null) {
            idReceta.isEnabled = false
            idReceta.setText(receta.idReceta.toString())
            nombre.setText(receta.nombre)
            descripcion.setText(receta.descripcion)
            pasos.setText(receta.pasos)
            tiempo.setText(receta.tiempo.toString())
        } else {
            idReceta.setText("0")
            nombre.setText("")
            descripcion.setText("")
            pasos.setText("")
            tiempo.setText("")
        }


        //spinner Dificultad
        val application = activity?.application as RegistroRecetaApplication
        val viewModelSpinner: DificultadViewModel = ViewModelProvider(requireActivity(),
            DificultadViewModelFactory(application.repository)
        ).get(DificultadViewModel::class.java)

        val listDificultad: Array<String>? = viewModelSpinner.getDificultadForSpinner()

        val spinner: Spinner = view.findViewById(R.id.spinnerDificultad)

        if (listDificultad != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listDificultad)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinner.adapter = spinnerArrayAdapter
        } //Fin Spinner Dificultad

        //Spinner Tipo
        val applicationTipo = activity?.application as RegistroRecetaApplication
        val viewModelSpinnerTipo: TipoViewModel = ViewModelProvider(requireActivity(),
            TipoViewModelFactory(applicationTipo.repository)
        ).get(TipoViewModel::class.java)

        val listTipo: Array<String>? = viewModelSpinnerTipo.getTipoForSpinner()

        val spinnerTipo: Spinner = view.findViewById(R.id.spinnerTipo)

        if (listTipo != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listTipo)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view

            spinnerTipo.adapter = spinnerArrayAdapter
        } //Fin Spinner Tipo

        guardarButton.setOnClickListener{
            if (idReceta.text.isNullOrBlank() ||
                nombre.text.isNullOrBlank()||
                descripcion.text.isNullOrBlank()||
                pasos.text.isNullOrBlank()||
                tiempo.text.isNullOrBlank()) {
                Toast.makeText(context, "Todos los campos son requeridos",
                    Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (receta != null) {
                Fuel.get(
                    "/recetas/ws_update_receta.php",
                    listOf(
                        "idReceta" to idReceta.text,
                        "nombre" to nombre.text,
                        "descripcion" to descripcion.text,
                        "pasos" to pasos.text,
                        "dificultad" to dificultad.selectedItem,
                        "tipo" to tipo.selectedItem,
                        "tiempo" to tiempo.text
                    )
                ).responseJson { _, _, result ->
                    requireActivity().runOnUiThread {
                        when (result) {
                            is Result.Failure -> {
                                Toast.makeText(context, result.getException().toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                            is Result.Success -> {
                                try {
                                    val data = result.get().obj()
                                    if (data.has("resultado") && data.getString("resultado") == "1") {
                                        Toast.makeText(context, "Registro actualizado",
                                            Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Registro no se pudo actualizar",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    findNavController().navigateUp()
                                } catch (e: Exception) {
                                    Toast.makeText(context, result.get().content,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                /*viewModel.update(
                    RecetaEntity(
                        receta.idReceta,
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        pasos.text.toString(),
                        dificultad.selectedItem.toString(),
                        tipo.selectedItem.toString(),
                        tiempo.text.toString().toInt()
                    )
                )*/
            } else {
                Fuel.get(
                    "/recetas/ws_insertar_receta.php",
                    listOf(
                        "idReceta" to idReceta.text,
                        "nombre" to nombre.text,
                        "descripcion" to descripcion.text,
                        "pasos" to pasos.text,
                        "dificultad" to dificultad.selectedItem,
                        "tipo" to tipo.selectedItem,
                        "tiempo" to tiempo.text
                    )
                ).responseJson { _, _, result ->
                    requireActivity().runOnUiThread {
                        when (result) {
                            is Result.Failure -> {
                                Toast.makeText(context, result.getException().toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                            is Result.Success -> {
                                try {
                                    val data = result.get().obj()
                                    if (data.has("resultado") && data.getString("resultado") == "1") {
                                        Toast.makeText(context, "Registro insertado",
                                            Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Registro no se pudo insertar",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    findNavController().navigateUp()
                                } catch (e: Exception) {
                                    Toast.makeText(context, result.get().content,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                /*viewModel.insert(
                    RecetaEntity(
                        idReceta.text.toString().toInt(),
                        nombre.text.toString(),
                        descripcion.text.toString(),
                        pasos.text.toString(),
                        dificultad.selectedItem.toString(),
                        tipo.selectedItem.toString(),
                        tiempo.text.toString().toInt()
                    )
                )*/
            }
        }

        /// Llamada a Dictado
        talkButton.setOnClickListener {
            dispatchSpeechIntent()
        }
    }
    // Implementacion de Dictdo

    private fun dispatchSpeechIntent() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hable ahora")
        startActivityForResult(intent, SPEECH_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_CAPTURE && data != null) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            textView.text = result?.get(0) ?: ""
        }
    }

}