package com.miprimersistemaweb.appcatalogo.repository

import android.content.Context
import com.miprimersistemaweb.appcatalogo.api.ProductoService
import com.miprimersistemaweb.appcatalogo.api.TokenInterceptor
import com.miprimersistemaweb.appcatalogo.db.LocalDataBase
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit

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
}