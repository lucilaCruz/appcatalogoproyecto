package com.miprimersistemaweb.appcatalogo.ui.catalogo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.miprimersistemaweb.appcatalogo.R
import com.miprimersistemaweb.appcatalogo.adapters.ProductoAdapter
import com.miprimersistemaweb.appcatalogo.beans.Producto
import com.miprimersistemaweb.appcatalogo.databinding.FragmentCatalogoBinding
import com.miprimersistemaweb.appcatalogo.repository.ProductoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject


class CatalogoFragment : Fragment() {
    //variable de rv
    private lateinit var rvProducto:RecyclerView
    private lateinit var productoAdapter: ProductoAdapter

    private var _binding: FragmentCatalogoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCatalogoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //inicializar componentes
        initComponentes(root)
        cargarProductosApi()
        return root
    }
    private fun initComponentes(root:View){
        rvProducto = root.findViewById(R.id.rvProductos)
    }
    private fun cargarProductosApi(){
        val productoRepository = ProductoRepository()
        val contexto = this.requireContext()
        CoroutineScope(Dispatchers.IO).launch{
            val respuesta = productoRepository.listarProducto(contexto)
            try {
                withContext(Dispatchers.Main){
                    if (respuesta.isSuccessful) {
                        //100,200,300
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.body()?.string()))
                        //convertir el json a un objeto
                        val jsonObjeto = JSONObject(prettyJson)
                        val jsonArrayData = jsonObjeto.getJSONArray("data")
                        cargarProductoAdapter(jsonArrayData)
                        Log.i("respuesta listado producto",prettyJson)
                    }else {
                        //400,500
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(JsonParser.parseString(respuesta.errorBody()?.string()))
                        Log.i("respuesta listado de productos",prettyJson)
                    }
                }
            }catch (error:Exception){
                Log.i("Error listado de productos",error.message.toString())
            }
        }
    }
    private fun cargarProductoAdapter(jsonArrayProductos:JSONArray){
        var listaProductos = ArrayList<Producto>()
        for (i in 0 until jsonArrayProductos.length() ){
            val productoApi = jsonArrayProductos.getJSONObject(i)
            var producto=Producto(
                productoApi.getInt("idproducto"),
                productoApi.getString("titulo"),
                productoApi.getDouble("precio"),
                productoApi.getString("nombreCategoria"),
                productoApi.getString("nombreMarca"),
                productoApi.getString("imagen")
            )
            listaProductos.add(producto)
        }
        productoAdapter = ProductoAdapter()
        productoAdapter.listaProductos = listaProductos
        val linearLayoutManager = LinearLayoutManager(this.requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val divider = DividerItemDecoration(this.requireContext(),linearLayoutManager.orientation)
        rvProducto.adapter = productoAdapter
        rvProducto.layoutManager = linearLayoutManager
        rvProducto.addItemDecoration(divider)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}