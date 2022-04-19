package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ClientService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUp : AppCompatActivity() {

    var txtFullName : TextInputEditText? = null
    var txtPhoneNumber: TextInputEditText? = null
    var txtEmail: TextInputEditText? = null
    var txtPassword: TextInputEditText? = null
    var btnSubmit: Button? = null

    private var txtLayoutFullName: TextInputLayout? = null
    private var txtLayoutPhoneNumber: TextInputLayout? = null
    private var txtLayoutEmail: TextInputLayout? = null
    private var txtLayoutPassword: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        txtFullName= findViewById(R.id.txtFullName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnSubmit = findViewById(R.id.btnSubmit)

        txtLayoutFullName = findViewById(R.id.txtLayoutFullName)
        txtLayoutPhoneNumber = findViewById(R.id.txtLayoutPhoneNumber)
        txtLayoutEmail = findViewById(R.id.txtLayoutEmail)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)

        btnSubmit!!.setOnClickListener {

            txtLayoutFullName!!.error = null
            txtLayoutPhoneNumber!!.error = null
            txtLayoutEmail!!.error = null
            txtLayoutPassword!!.error = null

            if (txtFullName?.text!!.isEmpty()) {
                txtLayoutFullName!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtPhoneNumber?.text!!.isEmpty()) {
                txtLayoutPhoneNumber!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtEmail?.text!!.isEmpty()) {
                txtLayoutEmail!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (!isEmailValid(txtEmail?.text.toString())){
                txtLayoutEmail!!.error = "Check your email !"

                return@setOnClickListener
            }
            if (txtPassword?.text!!.isEmpty()) {
                txtLayoutPassword!!.error = "must not be empty"
                return@setOnClickListener
            }


            ApiService.CLIENT_SERVICE.register(
                ClientService.ClientBody(
                    txtFullName!!.text.toString() ,
                    txtEmail!!.text.toString(),
                    txtPassword!!.text.toString(),
                    txtPhoneNumber!!.text.toString(),
                )
            ).enqueue(
                    object : Callback<ClientService.ClientResponse> {
                        override fun onResponse(
                            call: Call<ClientService.ClientResponse>,
                            response: Response<ClientService.ClientResponse>
                        ) {
                            if (response.code() == 200) {
                                val intent = Intent(this@SignUp, Login::class.java)
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

    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }


}