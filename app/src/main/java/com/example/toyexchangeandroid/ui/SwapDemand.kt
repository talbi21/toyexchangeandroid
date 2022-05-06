package com.example.toyexchangeandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.service.ApiService

class SwapDemand : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_demand)


        var intent = intent
        var id = intent.getIntExtra("id", 0)
        var image = intent.getStringExtra("image")

        var txtDtId = findViewById<TextView>(R.id.nametv)
        var dImage=findViewById<ImageView>(R.id.ToyPicClient1)


        Glide.with(dImage).load(ApiService.BASE_URL + image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(dImage)
    }
}