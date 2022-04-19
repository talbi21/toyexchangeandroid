package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ClientService
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUp : AppCompatActivity() {

    var txtFullName : TextInputEditText? = null
    var txtEmail: TextInputEditText? = null
    var txtPassword: TextInputEditText? = null
    var txtPhoneNumber: TextInputEditText? = null
    var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        txtFullName= findViewById(R.id.txtFullName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit!!.setOnClickListener {
            ApiService.CLIENT_SERVICE.register(
                ClientService.ClientBody(
                    txtFullName!!.text.toString() ,
                    txtEmail!!.text.toString(),
                    txtPassword!!.text.toString(),
                    txtPhoneNumber!!.text.toString(),
                )
            )
                .enqueue(
                    object : Callback<ClientService.ClientResponse> {
                        override fun onResponse(
                            call: Call<ClientService.ClientResponse>,
                            response: Response<ClientService.ClientResponse>
                        ) {
                            if (response.code() == 200) {
                                val intent =
                                    Intent(this@SignUp, Login::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<ClientService.ClientResponse>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )
        }

    }
}