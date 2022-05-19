package com.example.toyexchangeandroid.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ClientService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Settings : AppCompatActivity() {

    var txtNewPassword: TextInputEditText? = null
    var txtOldPassword: TextInputEditText? = null

    var btnSubmit: Button? = null

    private var txtLayoutOldPassword: TextInputLayout? = null
    private var txtLayoutNewPassword: TextInputLayout? = null


    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = this.getSharedPreferences(PREF_NAME, MODE_PRIVATE)


        btnSubmit = findViewById(R.id.btnUpdateProfile)

        txtOldPassword = findViewById(R.id.txtOldPassword)
        txtNewPassword = findViewById(R.id.txtNewPassword)

        txtLayoutOldPassword = findViewById(R.id.txtLayoutOldPassword)
        txtLayoutNewPassword = findViewById(R.id.txtLayoutNewPassword)

        btnSubmit!!.setOnClickListener {

            txtLayoutOldPassword!!.error = null
            txtLayoutNewPassword!!.error = null

            if (txtNewPassword?.text!!.isEmpty()) {
                txtLayoutNewPassword!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtOldPassword?.text!!.isEmpty()) {
                txtLayoutOldPassword!!.error = "must not be empty"
                return@setOnClickListener
            }

            val gson = Gson()
            val  us =  sharedPreferences.getString(myuser, "USER")

            nowuser = gson.fromJson(us,Client::class.java)

            ApiService.CLIENT_SERVICE.updatePassword(
                nowuser._id,
                ClientService.updatePassBody(
                    txtOldPassword!!.text.toString(),
                    txtNewPassword!!.text.toString()
                )
            ).enqueue(
                object : Callback<ClientService.ClientResponse> {
                    override fun onResponse(
                        call: Call<ClientService.ClientResponse>,
                        response: Response<ClientService.ClientResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent = Intent(this@Settings, Login::class.java)
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