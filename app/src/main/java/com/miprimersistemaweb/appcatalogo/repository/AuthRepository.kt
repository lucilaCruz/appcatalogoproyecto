package com.miprimersistemaweb.appcatalogo.repository

import com.miprimersistemaweb.appcatalogo.api.AuthService
import com.miprimersistemaweb.appcatalogo.beans.Usuario
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody

import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit

class AuthRepository {
    suspend fun login(usuario: Usuario):Response<ResponseBody>{
        val retrofit = Retrofit.Builder().baseUrl("https://miprimersistemaweb.com").build()
        val service = retrofit.create(AuthService::class.java)
        val bodyJson = JSONObject()
        bodyJson.put("email",usuario.email)
        bodyJson.put("password",usuario.password)
        val bodyJsonString = bodyJson.toString()
        val requestBody:RequestBody = bodyJsonString.toRequestBody("application/json".toMediaTypeOrNull())
        return service.loginapi(requestBody)
    }
}