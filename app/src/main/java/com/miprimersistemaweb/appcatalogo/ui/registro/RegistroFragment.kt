package com.miprimersistemaweb.appcatalogo.ui.registro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.miprimersistemaweb.appcatalogo.R
import com.miprimersistemaweb.appcatalogo.beans.Categoria
import com.miprimersistemaweb.appcatalogo.beans.Marca
import com.miprimersistemaweb.appcatalogo.databinding.FragmentRegistroBinding
import com.miprimersistemaweb.appcatalogo.repository.ProductoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class RegistroFragment : Fragment() {
    private lateinit var spCategoria:Spinner
    private lateinit var spMarca:Spinner
    private lateinit var txtTituloProducto:EditText
    private lateinit var  txtPrecioProducto:EditText
    private lateinit var imgProducto:ImageView
    private lateinit var btnGuardarProducto:Button
    private lateinit var imagenUri: Uri

    private var categoriaSeleccionada=""
    private var marcaSeleccionada=""

    private var _binding: FragmentRegistroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)*/

        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initComponentes(root)
        cargarCategoriaApi()
        cargarMarcaApi()
        initEventos()
        return root
    }
    private fun initComponentes(root:View){
        spCategoria = root.findViewById(R.id.spCategoria)
        spMarca = root.findViewById(R.id.spMarca)
        txtTituloProducto = root.findViewById(R.id.txtTituloProducto)
        txtPrecioProducto = root.findViewById(R.id.txtPrecioProducto)
        btnGuardarProducto = root.findViewById(R.id.btnGuardarProducto)
        imgProducto = root.findViewById(R.id.imgProducto)

    }
    private fun initEventos(){
        btnGuardarProducto.setOnClickListener {
            if (validar()){
                guardarProductoApi()
            }
        }
        imgProducto.setOnClickListener {
            val imagenActivity = Intent(Intent.ACTION_OPEN_DOCUMENT,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            seleccionarImagen.launch(imagenActivity)

        }
    }
    private val seleccionarImagen = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        it ->
        if (it.resultCode == Activity.RESULT_OK){
            val data = it.data
            data?.data?.let{
                imagenUri = it
                imgProducto.setImageURI(imagenUri)
            }
        }
    }
    private fun validar():Boolean{
        return true
    }
    private fun guardarProductoApi(){

    }
    private fun cargarCategoriaApi(){
        val productoRepository = ProductoRepository()
        val contexto = this.requireContext()
        CoroutineScope(Dispatchers.IO).launch{
            val respuesta = productoRepository.listarCategoria(contexto)
            try {
                withContext(Dispatchers.Main){
                    if (respuesta.isSuccessful) {
                        //100,200,300
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.body()?.string()))
                        //convertir el json a un objeto
                        val jsonObjeto = JSONObject(prettyJson)
                        val jsonArrayCategorias = jsonObjeto.getJSONArray("data")
                        initCatagorias(jsonArrayCategorias)
                        Log.i("respuesta listado categorias",prettyJson)
                    }else {
                        //400,500
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.errorBody()?.string()))
                        Log.i("respuesta listado de categorias",prettyJson)
                    }
                }
            }catch (error:Exception){
                Log.i("Error listado de categorias",error.message.toString())
            }
        }
    }
    private fun initCatagorias(arrayCategorias:JSONArray){
        val opcionesCategorias = ArrayList<Categoria>()
        for (i in 0 until arrayCategorias.length()){
            val categoria = Categoria(
                arrayCategorias.getJSONObject(i).getString("idcategoria"),
                arrayCategorias.getJSONObject(i).getString("nombre"))
            opcionesCategorias.add(categoria)
        }
        spCategoria.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSelecionada = parent?.getItemAtPosition(position) as Categoria
                val idOpcionSeleccionada = opcionSelecionada.id
                categoriaSeleccionada = idOpcionSeleccionada
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                categoriaSeleccionada = ""
            }
        }
        val categoriaAdapter:ArrayAdapter<Categoria> = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item,opcionesCategorias)
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spCategoria.adapter=categoriaAdapter

    }
    //cargar marcas
    private fun cargarMarcaApi(){
        val productoRepository = ProductoRepository()
        val contexto = this.requireContext()
        CoroutineScope(Dispatchers.IO).launch{
            val respuesta = productoRepository.listarMarca(contexto)
            try {
                withContext(Dispatchers.Main){
                    if (respuesta.isSuccessful) {
                        //100,200,300
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.body()?.string()))
                        //convertir el json a un objeto
                        val jsonObjeto = JSONObject(prettyJson)
                        val jsonArrayMarcas = jsonObjeto.getJSONArray("data")
                        initMarcas(jsonArrayMarcas)
                        Log.i("respuesta listado marcas",prettyJson)
                    }else {
                        //400,500
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.errorBody()?.string()))
                        Log.i("respuesta listado de marcas",prettyJson)
                    }
                }
            }catch (error:Exception){
                Log.i("Error listado de marcas",error.message.toString())
            }
        }
    }
    private fun initMarcas(arrayMarcas:JSONArray){
        val opcionesMarcas = ArrayList<Marca>()
        for (i in 0 until arrayMarcas.length()){
            val marca = Marca(arrayMarcas.getJSONObject(i).getString("idmarca"),
                arrayMarcas.getJSONObject(i).getString("nombre"))
            opcionesMarcas.add(marca)
        }
        spMarca.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val opcionSelecionada = parent?.getItemAtPosition(position) as Marca
                val idOpcionSeleccionada = opcionSelecionada.id
                marcaSeleccionada = idOpcionSeleccionada
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                marcaSeleccionada = ""
            }
        }
        val marcaAdapter:ArrayAdapter<Marca> = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_item,opcionesMarcas)
        marcaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spMarca.adapter=marcaAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}