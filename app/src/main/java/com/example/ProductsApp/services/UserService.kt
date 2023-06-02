package com.example.ProductsApp.services

import com.example.ProductsApp.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/auth/login")
    fun login(@Body user: User) :Call<ResponseBody>

}