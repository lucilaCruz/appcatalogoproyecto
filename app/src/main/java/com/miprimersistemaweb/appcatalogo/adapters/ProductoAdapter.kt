package com.miprimersistemaweb.appcatalogo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.miprimersistemaweb.appcatalogo.R
import com.miprimersistemaweb.appcatalogo.beans.Producto
import java.util.ArrayList

class ProductoAdapter:RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {
    var listaProductos:List<Producto> = ArrayList<Producto>()
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val imgProducto:ImageView
        val txtTituloProducto:TextView
        val txtMarcaProducto:TextView
        val txtCategoriaProducto:TextView
        val txtPrecioProducto:TextView
        init {
            imgProducto = view.findViewById(R.id.imgProducto)
            txtTituloProducto = view.findViewById(R.id.txtNombreUsuario)
            txtMarcaProducto = view.findViewById(R.id.txtMarcaProducto)
            txtCategoriaProducto = view.findViewById(R.id.txtCategoriaProducto)
            txtPrecioProducto = view.findViewById(R.id.txtPrecioProducto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //crea la relacion con el archivo xml item_producto.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //retorna la cantidad de productos
        return listaProductos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaProductos.get(position)
        holder.imgProducto.load(item.imagen)
        holder.txtTituloProducto.text = item.titulo
        holder.txtCategoriaProducto.text = item.nombreCategoria
        holder.txtMarcaProducto.text = item.nombreMarca
        holder.txtPrecioProducto.text = "S/. ${item.precio}"
    }
}