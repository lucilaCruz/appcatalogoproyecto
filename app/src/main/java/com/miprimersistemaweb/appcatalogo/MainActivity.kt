package com.miprimersistemaweb.appcatalogo

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
            Toast.makeText(this,"iniciar sesion",Toast.LENGTH_LONG).show()
        }
        btnRegistrarse.setOnClickListener {
            Toast.makeText(this,"registrarse",Toast.LENGTH_LONG).show()
        }
    }

}