package com.miprimersistemaweb.appcatalogo.repository

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.miprimersistemaweb.appcatalogo.api.ProductoService
import com.miprimersistemaweb.appcatalogo.api.TokenInterceptor
import com.miprimersistemaweb.appcatalogo.beans.Producto
import com.miprimersistemaweb.appcatalogo.db.LocalDataBase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

class ProductoRepository {
    suspend fun listarProducto(contexto:Context): Response<ResponseBody> {
        //conexion a la base de datos
        val localBase = LocalDataBase.getInstance(contexto)
        val usuarioDao = localBase.usuarioDao()
        val usuario = usuarioDao.listar()
        //interceptor
        val cliente = OkHttpClient.Builder().addInterceptor(TokenInterceptor("Bearer",usuario.token)).build()
        val retrofit = Retrofit.Builder().client(cliente).baseUrl("https://miprimersistemaweb.com").build()
        val service = retrofit.create(ProductoService::class.java)

        return service.listarProductoSer()
    }
    //listar categoria
    suspend fun listarCategoria(contexto: Context):Response<ResponseBody>{
        //conexion a la base de datos
        val localBase = LocalDataBase.getInstance(contexto)
        val usuarioDao = localBase.usuarioDao()
        val usuario = usuarioDao.listar()
        //interceptor
        val cliente = OkHttpClient.Builder().addInterceptor(TokenInterceptor("Bearer",usuario.token)).build()
        val retrofit = Retrofit.Builder().client(cliente).baseUrl("https://miprimersistemaweb.com").build()
        val service = retrofit.create(ProductoService::class.java)
        return service.listarCategoriaProducto()
    }

    //listar marca
    suspend fun listarMarca(contexto: Context):Response<ResponseBody>{
        //conexion a la base de datos
        val localBase = LocalDataBase.getInstance(contexto)
        val usuarioDao = localBase.usuarioDao()
        val usuario = usuarioDao.listar()
        //interceptor
        val cliente = OkHttpClient.Builder().addInterceptor(TokenInterceptor("Bearer",usuario.token)).build()
        val retrofit = Retrofit.Builder().client(cliente).baseUrl("https://miprimersistemaweb.com").build()
        val service = retrofit.create(ProductoService::class.java)
        return service.listarMarcasProducto()
    }

    //registrar producto
    suspend fun registrarProdcucto(context:Context, producto: Producto, imagenUri:Uri):Response<ResponseBody>{
        //
        val localBase = LocalDataBase.getInstance(context)
        val usuarioDao = localBase.usuarioDao()
        val usuario = usuarioDao.listar()
        //interceptor
        val cliente = OkHttpClient.Builder().addInterceptor(TokenInterceptor("Bearer",usuario.token)).build()
        val retrofit = Retrofit.Builder().client(cliente).baseUrl("https://miprimersistemaweb.com").build()
        val service = retrofit.create(ProductoService::class.java)

        val titulo =producto.titulo.toRequestBody("text/plain".toMediaTypeOrNull())
        val precio =producto.precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val idcategoria =producto.idCategoria.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val idmarca = producto.idMarca.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        //imagen
        val mimeType = context.contentResolver.getType(imagenUri)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)?:"jpg"
        val file = File(context.cacheDir,"archivo_Temporal_${System.currentTimeMillis()}.extension")
        val inputStream = context.contentResolver.openInputStream(imagenUri)
        inputStream?.use{
            input -> file.outputStream().use{ output ->
                input.copyTo(output)
             }
        }
        val mediaType = when(extension){
            "jpg","jpeg"->"image/jpeg"
            "png"->"image/png"
            else -> "multipart/form-data"
        }.toMediaTypeOrNull()
        val imagen = file.asRequestBody(mediaType)
        val imagenBody = MultipartBody.Part.createFormData("imagen",file.name,imagen)
        return service.registrarProducto(titulo,idmarca,idcategoria,precio,imagenBody)
    }
}