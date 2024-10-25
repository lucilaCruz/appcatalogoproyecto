package com.miprimersistemaweb.appcatalogo.api


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductoService {
    @GET("api/productos/list")
    suspend fun listarProductoSer():Response<ResponseBody>
    //listar categorias
    @GET("/api/categorias/list")
    suspend fun listarCategoriaProducto():Response<ResponseBody>

    //@listar marcas
    @GET("/api/marcas/list")
    suspend fun listarMarcasProducto():Response<ResponseBody>
    //registrar producto
    @Multipart
    @POST("/api/productos/create")
    suspend fun registrarProducto(@Part("titulo") titulo:ResponseBody,
                                  @Part("idmarca") idmarca:RequestBody,
                                  @Part("idcategoria") idcategoria:RequestBody,
                                  @Part("precio") precio:RequestBody,
                                  @Part imagen:MultipartBody.Part):Response<ResponseBody>


}