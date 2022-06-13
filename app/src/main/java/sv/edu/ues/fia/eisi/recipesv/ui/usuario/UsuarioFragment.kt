package sv.edu.ues.fia.eisi.recipesv.ui.usuario

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.shashank.sony.fancytoastlib.FancyToast
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.RegistroRecetaApplication
import sv.edu.ues.fia.eisi.recipesv.db.UsuarioEntity
import sv.edu.ues.fia.eisi.recipesv.entity.Usuario
import java.io.File
import java.io.FileOutputStream

class UsuarioFragment : Fragment(), UsuarioListAdapter.OnUsuarioClickListener {

    var listaUsuarios: ArrayList<Usuario> = ArrayList()

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

        var msjError : String?=null
        var usuarios : Usuario?=null
        var gson : Gson = Gson()

        Fuel.get(
            "/usuarios/ws_get_all_usuarios.php"

        ).responseJson { _, _, result ->
            requireActivity().runOnUiThread {
                when (result) {
                    is Result.Failure -> {
                        msjError = result.getException().toString();
                        //Toast.makeText(this@MainActivity, result.getException().toString(), Toast.LENGTH_LONG).show()
                    }
                    is Result.Success -> {
                        val data = result.get().array()
                        if (data.length() > 0) {
                            for (i in 0..data.length()-1) {
                                var jsonObject: String? = data.getJSONObject(i).toString()

                                if (jsonObject != null) {
                                    usuarios = gson.fromJson(jsonObject, Usuario::class.java)
                                    usuarios?.let { listaUsuarios.add(it) }
                                }
                            }
                            //msjError = "Encontrado: " + data.getJSONObject(0).getString("nombre");
                            //Toast.makeText(this@MainActivity, "Encontrado: " + data.getJSONObject(0).getString("EMAIL"), Toast.LENGTH_LONG).show()
                        } else {
                            msjError = "Usuario o contraseña incorrectos.";
                            //Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            viewModel.usuarioActual = null
            findNavController().navigate(R.id.action_nav_usuario_to_nav_guardar_usuario)
        }

        //iTextPDF
        if(checkPermission()) {
            FancyToast.makeText(context, "Permiso Aceptado", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
        } else {
            requestPermissions();
        }

        val btnCrearPdf: Button = view.findViewById(R.id.btnCrearPdf)
        btnCrearPdf.setOnClickListener {
            viewModel.usuarioActual = null
            crearPdf()
            Toast.makeText(context, "Archivo PDF creado!", Toast.LENGTH_LONG).show()
        }

        //PDF_Viewer
        val btnVerPdf: Button = view.findViewById(R.id.btnVerPdf)
        btnVerPdf.setOnClickListener {
            viewModel.usuarioActual = null
            findNavController().navigate(R.id.action_nav_usuario_to_nav_pdfViewer)
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

    //iTextPDF
    fun crearPdf() {
        var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath+"/EjemploITextPDF"

        val dir = File(path)
        if (!dir.exists())
            dir.mkdirs()

        val file = File(dir, "usuarios.pdf")
        val fileOutputStream = FileOutputStream(file)

        val documento = Document()
        PdfWriter.getInstance(documento, fileOutputStream)

        documento.open()

        val titulo = Paragraph(
            "Lista de Usuarios \n\n\n",
            FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLUE)
        )

        documento.add(titulo)

        var tabla = PdfPTable(3)
        tabla.addCell("USUARIO")
        tabla.addCell("EMAIL")
        tabla.addCell("CONTRASEÑA")

        for (item in listaUsuarios){
            tabla.addCell(item.nombre)
            tabla.addCell(item.email)
            tabla.addCell(item.password)
        }

        documento.add(tabla)

        documento.close()
    }

    private fun checkPermission(): Boolean {
        val permission1 =
            context?.let {
                ContextCompat.checkSelfPermission(it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        val permission2 =
            context?.let {
                ContextCompat.checkSelfPermission(it,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE),
            200
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 200) {
            if (grantResults.size > 0) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Toast.makeText(context, "Permiso concedido", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Permiso denegado", Toast.LENGTH_LONG).show()
                    //finish()
                }
            }
        }
    }
}