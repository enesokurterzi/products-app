package com.example.ProductsApp.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.ProductsApp.R
import com.example.ProductsApp.models.Product

class ProductGalleryCustomAdapter(private val context: Activity, private val list: List<Product>) : ArrayAdapter<Product>(context, R.layout.custom_list_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = context.layoutInflater.inflate(R.layout.custom_list_item, null, true)

        val r_image = rootView.findViewById<ImageView>(R.id.imageDetail)

        val product = list[position]

        Glide.with(rootView).load(product.thumbnail).into(r_image)

        return rootView
    }

}