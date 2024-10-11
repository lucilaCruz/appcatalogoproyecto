package com.miprimersistemaweb.appcatalogo

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.miprimersistemaweb.appcatalogo.beans.Usuario
import com.miprimersistemaweb.appcatalogo.db.LocalDataBase
import com.miprimersistemaweb.appcatalogo.db.entity.Usuario as UsuarioBd
import com.miprimersistemaweb.appcatalogo.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    //inicializar variables
    private lateinit var txtEmail:EditText
    private lateinit var txtPasswordLogin:EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrarse:Button
    private lateinit var txtMensajeError:TextView
    //alternar visibilidad password
    private lateinit var  icPasswordAlternar:ImageView
    private var esPassworVisible:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val localBase = LocalDataBase.getInstance(this)
        val usarioDao = localBase.usuarioDao()
        val usuario = usarioDao.listar()
        if (usuario!=null){
            //ir catalogo
            irHome()
        }else{
            inicializarComponentes()
            incializarEventos()
        }
    }
    private fun inicializarComponentes(){
        txtEmail = findViewById(R.id.txtEmailLogin)
        txtPasswordLogin=findViewById(R.id.txtPasswordLogin)
        btnIniciarSesion = findViewById(R.id.btnIngresar)
        btnRegistrarse=findViewById(R.id.btnRegistrarLogin)
        txtMensajeError=findViewById(R.id.txtMensajeError)
        //inicializar imagen eye
        icPasswordAlternar = findViewById(R.id.icPasswordAlternar)
    }
    private fun incializarEventos(){
        btnIniciarSesion.setOnClickListener {
            if (validacion()){
                //iniciar sesion
                apiLogin()
            }
            //Toast.makeText(this,"iniciar sesion",Toast.LENGTH_LONG).show()
        }
        btnRegistrarse.setOnClickListener {
           // Toast.makeText(this,"registrarse",Toast.LENGTH_LONG).show()
            irRegistroUsuario()
        }
        icPasswordAlternar.setOnClickListener{
            alternarPasswordVisible()
        }
    }
    private fun alternarPasswordVisible(){
        if (esPassworVisible){
            //ocultar contraseña
            txtPasswordLogin.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            icPasswordAlternar.setImageResource(R.drawable.ic_eye_off)
            esPassworVisible=false
        }else{
            //mostrar contraseña
            txtPasswordLogin.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            icPasswordAlternar.setImageResource(R.drawable.ic_eye)
            esPassworVisible=true
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
            txtEmail.error="Debe un ingresar su correo"
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
    private fun irHome(){
        val intentHome = Intent(this,HomeActivity::class.java)
        //intentRegistro.putExtra("nombre","Lucila")
        startActivity(intentHome)
        finish()
    }
    private fun apiLogin(){
        val authRepository = AuthRepository()
        val usuario = Usuario(0,"",txtEmail.text.toString(),txtPasswordLogin.text.toString())
        CoroutineScope(Dispatchers.IO).launch{
            //val respuesta = authRepository.registrar(usuario)
            val respuesta = authRepository.login(usuario)
            try {
                withContext(Dispatchers.Main){
                    if (respuesta.isSuccessful) {
                        //100,200,300
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.body()?.string()))
                       //convertir el json a un objeto
                        val jsonObjeto = JSONObject(prettyJson)
                        val jsonObjetoData = jsonObjeto.getJSONObject("data")
                        if(jsonObjetoData.has("Error")){
                            //mostrar el mensaje de error
                            txtMensajeError.text = jsonObjetoData.getString("Error")
                            txtMensajeError.visibility= View.VISIBLE
                        }else{
                            //guardar en la base el usuario
                            val usuarioBd = UsuarioBd(jsonObjetoData.getString("id").toInt(),
                                    jsonObjetoData.getString("name").toString(),
                                jsonObjetoData.getString("email").toString(),
                                jsonObjetoData.getString("token").toString()
                                )
                            guardarEnBd(usuarioBd)

                            //ingresar al catalogo
                            irHome()
                        }

                        Log.i("respuesta login",prettyJson)
                    }else {
                        //400,500
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.errorBody()?.string()))
                        //convertir json en objeto
                        val jsonObjeto = JSONObject(prettyJson)
                        txtMensajeError.text = jsonObjeto.getString("message")
                        txtMensajeError.visibility= View.VISIBLE
                        Log.i("respuesta login",prettyJson)
                    }
                }

            }catch (error:Exception){
                Log.i("Error login",error.message.toString())
            }
        }
    }
   private fun guardarEnBd(usuario: UsuarioBd){
       val localBase = LocalDataBase.getInstance(this)
       val usarioDao = localBase.usuarioDao()
       usarioDao.insert(usuario)
   }


}