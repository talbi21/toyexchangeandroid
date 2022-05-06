package com.example.toyexchangeandroid.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.ProfileItemAdapter
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.google.gson.Gson

class EditProfile : AppCompatActivity() {

    private lateinit var txtUserName: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPhoneNumber: TextView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client
    lateinit var image : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initView()
    }


    private fun initView() {




        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        txtEmail = findViewById(R.id.txtEmail)
        txtUserName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber)
        image = findViewById(R.id.imageView2)

        txtEmail.isEnabled = true
        txtPhoneNumber.isEnabled = true
        txtUserName.isEnabled = true

        //------------------
        val gson = Gson()
        val  us =  sharedPreferences.getString(myuser, "USER")

        nowuser = gson.fromJson(us, Client::class.java)
        print("///////////////////")
        print(nowuser)
        print("///////////////////")

        txtEmail.text = nowuser.email
        txtUserName.text = nowuser.userName
        txtPhoneNumber.text = nowuser.phoneNumber

        Glide.with(image).load(ApiService.BASE_URL + nowuser.image).placeholder(R.drawable.imageload).circleCrop()
            .error(R.drawable.default_user).into(image)

    }
}