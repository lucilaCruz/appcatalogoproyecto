package com.miprimersistemaweb.appcatalogo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.miprimersistemaweb.appcatalogo.databinding.ActivityHomeBinding
import com.miprimersistemaweb.appcatalogo.db.LocalDataBase
import com.miprimersistemaweb.appcatalogo.db.entity.Usuario

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    //agregar dos variables para nombre y el email
    private lateinit var txtNombreUsuario:TextView
    private lateinit var txtCorreoUsuario:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val headerView: View = navView.getHeaderView(0)
        txtNombreUsuario = headerView.findViewById(R.id.txtNombreUsuario)
        txtCorreoUsuario = headerView.findViewById(R.id.txtCorreoUsuario)
        obtenerDatosUsuario()
    }
    private fun obtenerDatosUsuario(){
        val localBase = LocalDataBase.getInstance(this)
        val usuarioDao = localBase.usuarioDao()
        val usuario = usuarioDao.listar()
        txtNombreUsuario.text=usuario.name
        txtCorreoUsuario.text=usuario.email
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.settings->{
                true
            }
            R.id.cerrarsesion->{
                cerrarSesion()
                irLogin()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }
    //cerrar sesion
    private fun cerrarSesion(){
        val localBase = LocalDataBase.getInstance(this)
        val usuarioDao = localBase.usuarioDao()
        val usuario = usuarioDao.listar()
        usuarioDao.eliminar(usuario)

    }
    private fun irLogin(){
        val intentLogin = Intent(this,MainActivity::class.java)
        startActivity(intentLogin)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}