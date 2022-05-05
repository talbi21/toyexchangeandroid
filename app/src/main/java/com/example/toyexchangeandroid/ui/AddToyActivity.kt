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
import com.example.toyexchangeandroid.service.ToyService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddToyActivity : AppCompatActivity() {

    var txtName : TextInputEditText? = null
    var txtDescription: TextInputEditText? = null
    var txtSize: TextInputEditText? = null
    var txtPrice: TextInputEditText? = null
    var btnAdd: Button? = null

    private var txtLayoutName: TextInputLayout? = null
    private var txtLayoutDescription: TextInputLayout? = null
    private var txtLayoutSize: TextInputLayout? = null
    private var txtLayoutPrice: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_toy)


        txtName= findViewById(R.id.txtName)
        txtDescription = findViewById(R.id.txtDescription)
        txtSize = findViewById(R.id.txtSize)
        txtPrice = findViewById(R.id.txtPrice)
        btnAdd = findViewById(R.id.btnAddToy)

        txtLayoutName = findViewById(R.id.txtLayoutName)
        txtLayoutDescription = findViewById(R.id.txtLayoutDescription)
        txtLayoutSize = findViewById(R.id.txtLayoutSize)
        txtLayoutPrice = findViewById(R.id.txtLayoutPrice)

        btnAdd!!.setOnClickListener {

            txtLayoutName!!.error = null
            txtLayoutDescription!!.error = null
            txtLayoutSize!!.error = null
            txtLayoutPrice!!.error = null

            if (txtName?.text!!.isEmpty()) {
                txtLayoutName!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtDescription?.text!!.isEmpty()) {
                txtLayoutDescription!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtSize?.text!!.isEmpty()) {
                txtLayoutSize!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtPrice?.text!!.isEmpty()){
                txtLayoutPrice!!.error = "Check your email !"

                return@setOnClickListener
            }

            ApiService.toyService.addPost(
                ToyService.ToyBody(
                    txtName!!.text.toString() ,
                    txtDescription!!.text.toString(),
                    txtPrice!!.text.toString(),
                    txtSize!!.text.toString(),
                    "",
                    "false",
                    ""
                )
            ).enqueue(
                object : Callback<ToyService.ToyResponse> {
                    override fun onResponse(
                        call: Call<ToyService.ToyResponse>,
                        response: Response<ToyService.ToyResponse>
                    ) {
                        if (response.code() == 200) {
                            Log.d("HTTP SUCCESS", "status code is " + response.code())
                        } else {
                            Log.d("HTTP ERROR", "status code is " + response.code())
                        }
                    }

                    override fun onFailure(
                        call: Call<ToyService.ToyResponse>,
                        t: Throwable
                    ) {
                        Log.d("FAIL", "fail")
                    }
                }
            )
        }



    }


}