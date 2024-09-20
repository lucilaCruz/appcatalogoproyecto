package com.miprimersistemaweb.appcatalogo.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    //login
    @POST("api/users/login")
    suspend fun loginapi(@Body requestBody: RequestBody):Response<ResponseBody>
    //registrar
    @POST("/api/users/create")
    suspend fun registrar(@Body requestBody: RequestBody):Response<ResponseBody>
}