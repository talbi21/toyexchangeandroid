package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ClientService
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {
    private var btnSubmit: Button? = null
    private var toSignUp: TextView? = null
    private var toForgotPassword: TextView? = null
    var txtEmail: TextInputEditText? = null
    var txtPassword: TextInputEditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnSubmit = findViewById(R.id.btnLogin)
        toSignUp = findViewById(R.id.ToSignUp)
        toForgotPassword = findViewById(R.id.ToForgotPassword)

        //onClick btn
        btnSubmit!!.setOnClickListener {
            ApiService.CLIENT_SERVICE.login(
                ClientService.LoginBody(
                    txtEmail!!.text.toString(),
                    txtPassword!!.text.toString()
                )
            )
                .enqueue(
                    object : Callback<ClientService.ClientResponse> {
                        override fun onResponse(
                            call: Call<ClientService.ClientResponse>,
                            response: Response<ClientService.ClientResponse>
                        ) {
                            if (response.code() == 200) {
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)

                                finish()
                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())

                                Toast.makeText(this@Login,"Please Check Your Information",Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(
                            call: Call<ClientService.ClientResponse>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail server $t")
                            Toast.makeText(this@Login,"Connection error",Toast.LENGTH_SHORT).show()

                        }
                    }
                )

        }

        toSignUp!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, SignUp::class.java)
            startActivity(mainIntent)

        });

        toForgotPassword!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, SentVerificationEmail::class.java)
            startActivity(mainIntent)

        });

    }
}