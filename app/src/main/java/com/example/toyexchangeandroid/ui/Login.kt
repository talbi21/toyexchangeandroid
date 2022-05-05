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
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Token
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ClientService
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val myuser = "USER"
const val PREF_NAME = "DATA_LOGIN"
const val email = "email"
const val password = "password"
const val IS_REMEMBRED = "IS_REMEMBRED"

class Login : AppCompatActivity() {
    var gson = Gson()

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


        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        if (mSharedPref.getBoolean(IS_REMEMBRED, false)){
            navigate()
        }

        //onClick btn
        btnSubmit!!.setOnClickListener {
            doLogin()
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

    private fun doLogin(){
        if (validate()){

            ApiService.CLIENT_SERVICE.login(
                ClientService.LoginBody(
                    txtEmail!!.text.toString(),
                    txtPassword!!.text.toString()
                )
            ).enqueue(
                object : Callback<Token> {
                    override fun onResponse(
                        call: Call<Token>,
                        response: Response<Token>
                    ) {
                        if (response.code() == 200) {
                            val token = gson.toJson(response.body())
                            val jsonObject = JSONTokener(token).nextValue() as JSONObject
                            val token2 = jsonObject.getString("token")
                            Log.d("token22",token2)
                            getUser(token2)

                        } else {
                            Log.d("HTTP ERROR", "status code is " + response.code())

                            Toast.makeText(this@Login,"Please Check Your Information",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<Token>,
                        t: Throwable
                    ) {
                        Log.d("FAIL", "fail server $t")
                        Toast.makeText(this@Login,"Connection error",Toast.LENGTH_SHORT).show()

                    }
                }
            )
        }
    }

    fun getUser(token: String) {
        //------------------get user from token------------------


        ApiService.CLIENT_SERVICE.getUserFromToken(
            ClientService.GetUserFromTokenBody(
                token
            )
        ).enqueue(
            object : Callback<Client> {
                override fun onResponse(
                    call: Call<Client>,
                    response: Response<Client>
                ) {
                    if (response.code() == 200) {
                        val clientJson = gson.toJson(response.body())
                        Log.d("clientJson",response.body().toString())

                        mSharedPref.edit().apply {
                            putString(myuser, clientJson)
                            putBoolean(IS_REMEMBRED, true)
                            putBoolean(IS_REMEMBRED, cbRememberMe.isChecked)

                        }.apply()
                        //------------------------------------
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("HTTP ERROR", "status code is " + response.code())

                        Toast.makeText(this@Login,"Please Check Your Information",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<Client>,
                    t: Throwable
                ) {
                    Log.d("FAIL", "fail server $t")
                    Toast.makeText(this@Login,"Connection error",Toast.LENGTH_SHORT).show()

                }
            }
        )
        //--------------------------------------------------------
    }


    private fun validate(): Boolean {
        txtLayoutEmail!!.error = null
        txtLayoutPassword!!.error = null

        //validate fields
        if (txtEmail?.text!!.isEmpty()) {
            txtLayoutEmail!!.error = "must not be empty"
            return false
        }
        if (!isEmailValid(txtEmail?.text.toString())){
            txtLayoutEmail!!.error = "Check your email !"

            return false
        }
        if (txtPassword?.text!!.isEmpty()) {
            txtLayoutPassword!!.error = "must not be empty"
            return false
        }
        //end validate fields

        return true
    }


    private fun navigate(){
        val mainIntent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(mainIntent)
    }

    fun isEmailValid(email: String?): Boolean {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

}