package com.example.toyexchangeandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.toyexchangeandroid.R
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.service.ApiService


class Details : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initView()
    }

    private fun initView() {


        var intent = intent
        var id = intent.getIntExtra("id", 0)
        var name = intent.getStringExtra("Name")
        var image = intent.getStringExtra("image")
        var description = intent.getStringExtra("description")
        var Price = intent.getStringExtra("Price")



        var txtDtId = findViewById<TextView>(R.id.nametv)
        var dImage=findViewById<ImageView>(R.id.imageView)
        var txtDtprice = findViewById<TextView>(R.id.pricetv)
        var txtDtdesc = findViewById<TextView>(R.id.desctv)



        txtDtId.text = name.toString()
        txtDtprice.text = Price.toString()
        txtDtdesc.text = description.toString()
        Glide.with(dImage).load(ApiService.BASE_URL + image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(dImage)


    }
}