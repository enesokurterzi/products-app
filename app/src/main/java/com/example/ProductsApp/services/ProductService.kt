package com.example.ProductsApp.services

import com.example.ProductsApp.models.Product
import com.example.ProductsApp.models.Products
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("/products")
    fun products(): Call<Products>

    @GET("/products")
    fun firstTenProduct( @Query("limit") limit: Int): Call<Products>

    @GET("/products/{id}")
    fun singleProduct( @Path("id") id: Int) : Call<Product>

    @GET("/products/search")
    fun searchProducts( @Query("q") q: String): Call<Products>

}