package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.UserService
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
            ApiService.userService.login(
                UserService.LoginBody(
                    txtEmail!!.text.toString(),
                    txtPassword!!.text.toString()
                )
            )
                .enqueue(
                    object : Callback<UserService.UserResponse> {
                        override fun onResponse(
                            call: Call<UserService.UserResponse>,
                            response: Response<UserService.UserResponse>
                        ) {
                            if (response.code() == 200) {
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<UserService.UserResponse>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )
        }

        toSignUp!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, SignUp::class.java)
            startActivity(mainIntent)

        });

        toForgotPassword!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, SignUp::class.java)
            startActivity(mainIntent)

        });

    }
}