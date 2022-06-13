package sv.edu.ues.fia.eisi.recipesv.ui

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.barteksc.pdfviewer.PDFView
import com.shashank.sony.fancytoastlib.FancyToast
import sv.edu.ues.fia.eisi.recipesv.R
import java.io.File

class PDFViewerFragment : Fragment() {

    lateinit var file : File
    lateinit var pdfView : PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_p_d_f_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath+"/EjemploITextPDF"
        file = File(path,"usuarios.pdf")

        pdfView= view.findViewById(R.id.pdfView) as PDFView
        pdfView.fromFile(file).load()
        FancyToast.makeText(context, "Prueba pdf", FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,false).show();
    }
}