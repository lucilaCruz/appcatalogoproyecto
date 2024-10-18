package com.miprimersistemaweb.appcatalogo.api


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ProductoService {
    @GET("api/productos/list")
    suspend fun listarProductoSer():Response<ResponseBody>
}