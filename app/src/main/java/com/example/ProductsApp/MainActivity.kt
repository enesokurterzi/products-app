package com.example.ProductsApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.ProductsApp.configs.ApiClient
import com.example.ProductsApp.models.User
import com.example.ProductsApp.services.UserService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var userService: UserService

    lateinit var txtUserName: TextView
    lateinit var txtPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userService = ApiClient.getClient().create(UserService::class.java)

        txtUserName = findViewById(R.id.txtUserName)
        txtPassword = findViewById(R.id.txtPassword)


    }

    fun btnLogin(view: View) {
        val userName = txtUserName.text.toString()
        val password = txtPassword.text.toString()
        val user = User(userName, password)

        if (userName.contains(" ") || password.contains(" ")) {

            Toast.makeText(this@MainActivity,"Your input has space, please remove them!", Toast.LENGTH_LONG).show()

        } else {

            userService.login(user).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val user = response.body()
                    if (user != null) {
                        val intent = Intent(this@MainActivity, ProductGalleryActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity,"Your username or password is wrong!", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("login", t.toString())
                }

            })
        }

    }
}