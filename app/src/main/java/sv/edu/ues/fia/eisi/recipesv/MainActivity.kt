package sv.edu.ues.fia.eisi.recipesv

import android.app.NotificationChannel
import android.app.NotificationManager
import android.icu.number.NumberFormatter.with
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.GlideContext
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import sv.edu.ues.fia.eisi.recipesv.databinding.ActivityMainBinding
import sv.edu.ues.fia.eisi.recipesv.db.*
import sv.edu.ues.fia.eisi.recipesv.entity.Usuario
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    //lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        FuelManager.instance.basePath = "https://gd19016pdm115.000webhostapp.com/"

        setContentView(R.layout.activity_login)

        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_reset = findViewById(R.id.btn_reset) as Button
        var btn_submit = findViewById(R.id.btn_submit) as Button
        val gson = Gson()

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

            if (user_name.isNullOrBlank()) {
                Toast.makeText(this@MainActivity, "Usuario es requerido.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.isNullOrBlank()) {
                Toast.makeText(this@MainActivity, "Contraseña es requerido.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            /*var usuario: UsuarioEntity?

            val application = this.application as RegistroRecetaApplication

            runBlocking {
                val resultado = async { application.repository.getUsuario(user_name.toString(), password.toString()) }
                runBlocking{
                    usuario = resultado.await()
                }
            }

            if (usuario != null) {
                Toast.makeText(this@MainActivity, "Bienvenido " + usuario!!.nombre, Toast.LENGTH_LONG).show()
                application.usuarioLogueado = usuario
                /*val intent = Intent(this, RecipesActivity::class.java)
                // start your next activity
                startActivity(intent)*/

                setContentView(R.layout.activity_main)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Create the NotificationChannel
                    val name = getString(R.string.channel_name)
                    val descriptionText = getString(R.string.channel_description)
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val CHANNEL_ID = "notif_recipe_sv"
                    val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                    mChannel.description = descriptionText
                    // Register the channel with the system; you can't change the importance
                    // or other notification behaviors after this
                    val notificationManager = ContextCompat.getSystemService(this, NotificationManager::class.java) as NotificationManager
                    notificationManager.createNotificationChannel(mChannel)
                }

                val toolbar: Toolbar = findViewById(R.id.toolbar)
                setSupportActionBar(toolbar)
                val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
                val navView: NavigationView = findViewById(R.id.nav_view)
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                // Passing each menu ID as a set of Ids because each
                // menu should be considered as top level destinations.
                appBarConfiguration = AppBarConfiguration(setOf(
                    R.id.nav_inicio, R.id.nav_recetas, R.id.nav_usuarios, R.id.nav_colecciones, R.id.nav_ingredientes), drawerLayout)
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)

            } else {
                Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show()
            }*/

            var msjError: String? = null;
            var usuario: Usuario? = null

            Fuel.get(
                "/usuarios/login.php",
                listOf("email" to user_name, "password" to password)
            ).responseJson { _, _, result ->
                this@MainActivity.runOnUiThread {
                    when (result) {
                        is Result.Failure -> {
                            msjError = result.getException().toString();
                            //Toast.makeText(this@MainActivity, result.getException().toString(), Toast.LENGTH_LONG).show()
                        }
                        is Result.Success -> {
                            val data = result.get().array()
                            if (data.length() > 0) {
                                var jsonObject: String? = data.getJSONObject(0).toString()
                                if (jsonObject != null) {
                                    usuario = gson.fromJson(jsonObject, Usuario::class.java)
                                }
                                //msjError = "Encontrado: " + data.getJSONObject(0).getString("nombre");
                                //Toast.makeText(this@MainActivity, "Encontrado: " + data.getJSONObject(0).getString("EMAIL"), Toast.LENGTH_LONG).show()
                            } else {
                                msjError = "Usuario o contraseña incorrectos.";
                                //Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    if (usuario != null) {
                        val application = this.application as RegistroRecetaApplication
                        FancyToast.makeText(this@MainActivity, "Bienvenido " + usuario!!.nombre, FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,false).show()
                        application.usuarioLogueado = usuario
                        /*val intent = Intent(this, RecipesActivity::class.java)
                        // start your next activity
                        startActivity(intent)*/

                        setContentView(R.layout.activity_main)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            // Create the NotificationChannel
                            val name = getString(R.string.channel_name)
                            val descriptionText = getString(R.string.channel_description)
                            val importance = NotificationManager.IMPORTANCE_DEFAULT
                            val CHANNEL_ID = "notif_recipe_sv"
                            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                            mChannel.description = descriptionText
                            // Register the channel with the system; you can't change the importance
                            // or other notification behaviors after this
                            val notificationManager = ContextCompat.getSystemService(this, NotificationManager::class.java) as NotificationManager
                            notificationManager.createNotificationChannel(mChannel)
                        }

                        val toolbar: Toolbar = findViewById(R.id.toolbar)
                        setSupportActionBar(toolbar)
                        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
                        val navView: NavigationView = findViewById(R.id.nav_view)
                        val navController = findNavController(R.id.nav_host_fragment_content_main)
                        // Passing each menu ID as a set of Ids because each
                        // menu should be considered as top level destinations.
                        appBarConfiguration = AppBarConfiguration(setOf(
                            R.id.nav_inicio, R.id.nav_recetas, R.id.nav_usuarios, R.id.nav_colecciones, R.id.nav_ingredientes), drawerLayout)
                        setupActionBarWithNavController(navController, appBarConfiguration)
                        navView.setupWithNavController(navController)

                        //Glide

                        var imageView:ImageView=findViewById(R.id.imageGlide)

                        Glide.with(this)
                            .load("https://cdn.pixabay.com/photo/2018/02/05/19/12/strawberry-3132973__340.jpg") // image url
/*                            .placeholder(R.drawable.placeholder) // any placeholder to load at start
                            .error(R.drawable.imagenotfound)  // any image in case of error*/
                            .override(277, 120) // resizing
                            .centerCrop()
                            .into(imageView);

                    } else {
                        Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos.", Toast.LENGTH_LONG).show()
                    }
                }
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



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


