package com.example.ProductsApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ProductsApp.adapters.ProductGalleryCustomAdapter
import com.example.ProductsApp.configs.ApiClient
import com.example.ProductsApp.models.Products
import com.example.ProductsApp.services.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductGalleryActivity : AppCompatActivity() {

    lateinit var productService: ProductService
    lateinit var txtSearch: TextView
    lateinit var productListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_gallery)

        txtSearch = findViewById(R.id.txtSearch)
        productListView = findViewById(R.id.productListView)

        productService = ApiClient.getClient().create(ProductService::class.java)


        productService.firstTenProduct(10).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {

                val products = response.body()!!.products
                val customAdapter = ProductGalleryCustomAdapter(this@ProductGalleryActivity, products)
                productListView.adapter = customAdapter

                productListView.setOnItemClickListener { adapterView, view, i, l ->

                    val element = customAdapter.getItem(i)
                    val intent = Intent(this@ProductGalleryActivity, DetailActivity::class.java)
                    intent.putExtra("itemId", element!!.id)
                    startActivity(intent)

                }

            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("firstTenProduct", t.toString())
            }

        })

    }

    fun btnSearch(view: View) {
        val searchString = txtSearch.text.toString()
        if (searchString.isNotEmpty()) {

            productService.searchProducts(searchString).enqueue(object : Callback<Products>{
                override fun onResponse(call: Call<Products>, response: Response<Products>) {

                    val products = response.body()!!.products
                    val customAdapter = ProductGalleryCustomAdapter(this@ProductGalleryActivity, products)
                    productListView.adapter = customAdapter

                    productListView.setOnItemClickListener { adapterView, view, i, l ->

                        val element = customAdapter.getItem(i)
                        val intent = Intent(this@ProductGalleryActivity, DetailActivity::class.java)
                        intent.putExtra("itemId", element!!.id)
                        startActivity(intent)

                    }

                }

                override fun onFailure(call: Call<Products>, t: Throwable) {
                    Log.e("searchProduct", t.toString())
                }

            })

        } else {
            Toast.makeText(this,"Your search is empty!", Toast.LENGTH_LONG).show()
        }
    }
}