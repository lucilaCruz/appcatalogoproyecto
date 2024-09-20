package com.miprimersistemaweb.appcatalogo

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //inicializar variables
    private lateinit var txtEmail:EditText
    private lateinit var txtPasswordLogin:EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrarse:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        inicializarComponentes()
        incializarEventos()

    }
    private fun inicializarComponentes(){
        txtEmail = findViewById(R.id.txtEmailLogin)
        txtPasswordLogin=findViewById(R.id.txtPasswordLogin)
        btnIniciarSesion = findViewById(R.id.btnIngresar)
        btnRegistrarse=findViewById(R.id.btnRegistrarLogin)
    }
    private fun incializarEventos(){
        btnIniciarSesion.setOnClickListener {
            if (validacion()){
                //iniciar sesion
            }
            //Toast.makeText(this,"iniciar sesion",Toast.LENGTH_LONG).show()
        }
        btnRegistrarse.setOnClickListener {
           // Toast.makeText(this,"registrarse",Toast.LENGTH_LONG).show()
            irRegistroUsuario()
        }
    }
    private fun irRegistroUsuario(){
        val intentRegistro = Intent(this,RegistroActivity::class.java)
        intentRegistro.putExtra("nombre","Lucila")
        startActivity(intentRegistro)
        finish()
    }
    private fun validacion():Boolean{
        var esValido = true
        if (txtEmail.text.toString().isBlank()){
            txtEmail.error="Debe un correo"
            esValido=false
        }
        if (txtPasswordLogin.text.toString().isBlank()){
            txtPasswordLogin.error="Debe ingresar su contraseña"
            esValido=false
        }
        if (!esValido){
            Toast.makeText(this,"Error en los datos por favor verificar",Toast.LENGTH_LONG).show()
        }

        return esValido
    }


}