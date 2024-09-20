package com.miprimersistemaweb.appcatalogo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val nombre = intent.getStringExtra("nombre")
        Toast.makeText(this,"el nombre es: $nombre",Toast.LENGTH_LONG).show()
    }
}