package com.example.toyexchangeandroid.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ClientService
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val PREF_NAME = "DATA_CV_PREF"
const val email = "email"
const val password = "password"
const val IS_REMEMBRED = "remembred"

class Login : AppCompatActivity() {
    private var btnSubmit: Button? = null
    private var toSignUp: TextView? = null
    private var toForgotPassword: TextView? = null

    private var txtEmail: TextInputEditText? = null
    private var txtPassword: TextInputEditText? = null

    lateinit var cbRememberMe: CheckBox
    lateinit var mSharedPref: SharedPreferences

    private var txtLayoutEmail: TextInputLayout? = null
    private var txtLayoutPassword: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnSubmit = findViewById(R.id.btnLogin)
        toSignUp = findViewById(R.id.ToSignUp)
        toForgotPassword = findViewById(R.id.ToForgotPassword)

        txtLayoutEmail = findViewById(R.id.txtLayoutEmail)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)
        cbRememberMe = findViewById(R.id.cbRememberMe)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        //onClick btn
        btnSubmit!!.setOnClickListener {
            txtLayoutEmail!!.error = null
            txtLayoutPassword!!.error = null

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

            mSharedPref.edit().apply {
                putString(email, txtEmail!!.text.toString())
                putString(password, txtPassword!!.text.toString())
                putBoolean(IS_REMEMBRED, cbRememberMe.isChecked)

            }.apply()

            if (cbRememberMe.isChecked){
                mSharedPref.edit().apply{
                    putBoolean(IS_REMEMBRED, cbRememberMe.isChecked)
                }.apply()
            }

            ApiService.CLIENT_SERVICE.login(
                ClientService.LoginBody(
                    txtEmail!!.text.toString(),
                    txtPassword!!.text.toString()
                )
            ).enqueue(
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

    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

}