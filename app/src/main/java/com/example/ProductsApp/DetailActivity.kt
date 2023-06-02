package com.example.ProductsApp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ProductsApp.configs.ApiClient
import com.example.ProductsApp.models.Product
import com.example.ProductsApp.services.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    lateinit var productService: ProductService

    lateinit var imageDetail: ImageView
    lateinit var txtDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageDetail = findViewById(R.id.imageDetail)
        txtDetail = findViewById(R.id.txtDetail)
        val itemId = intent.getLongExtra("itemId", 0)

        productService = ApiClient.getClient().create(ProductService::class.java)

        productService.singleProduct(itemId.toInt()).enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val product = response.body()
                if (product != null) {
                    txtDetail.text = product.description
                    Glide.with(this@DetailActivity).load(product.thumbnail).into(imageDetail)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("singleProduct", t.toString())
            }

        })

    }
}