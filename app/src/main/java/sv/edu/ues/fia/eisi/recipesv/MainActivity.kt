package sv.edu.ues.fia.eisi.recipesv

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import sv.edu.ues.fia.eisi.recipesv.db.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_reset = findViewById(R.id.btn_reset) as Button
        var btn_submit = findViewById(R.id.btn_submit) as Button

        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            et_user_name.setText("")
            et_password.setText("")
        }

        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;

            /*val repository: RegistroRecetaRepository = RegistroRecetaRepository(RegistroRecetaDB.getDatabase(this))
            val application = activity?.application as RegistroRecetaApplication
            val db: RegistroRecetaDB

            val usuarioDao: UsuarioDao? = UsuarioDao()*/

            var usuario: UsuarioEntity?

            val application = this.application as RegistroRecetaApplication

            runBlocking {
                val resultado = async { application.repository.getUsuario(user_name.toString(), password.toString()) }
                runBlocking{
                    usuario = resultado.await()
                }
            }

            if (usuario != null) {
                Toast.makeText(this@MainActivity, "Bienvenido " + usuario!!.nombre, Toast.LENGTH_LONG).show()
                val intent = Intent(this, RecipesActivity::class.java)
                // start your next activity
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show()
            }



            /*val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            val navView: NavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_inicio, R.id.nav_recetas, R.id.nav_usuarios, R.id.nav_colecciones), drawerLayout)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)*/
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}