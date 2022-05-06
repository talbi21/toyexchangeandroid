package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.service.ApiService

class ToyDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toy_details)
        initView()
    }
    var btnDemand: Button? = null

    private fun initView() {
        btnDemand = findViewById(R.id.btnDemand)


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


        btnDemand!!.setOnClickListener {
            val mainIntent = Intent(this, SwapDemand::class.java)
            startActivity(mainIntent)
            finish()
        }

    }
}