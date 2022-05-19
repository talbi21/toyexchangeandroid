package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.util.AppDataBase

import com.google.android.material.snackbar.Snackbar

class ToyDetails : AppCompatActivity() {

    lateinit var imageButton : ImageButton
    lateinit var  id :String
    lateinit var  name :String
    lateinit var  image :String
    lateinit var description :String
    lateinit var  Price :String
    lateinit var  Owner :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toy_details)
        initView()
        lateinit var educationList : MutableList<Toy>
        lateinit var dataBase: AppDataBase
        var test = 0
        dataBase = AppDataBase.getDatabase(this)
        educationList = dataBase.toydao().getAllEducations()
        imageButton = findViewById(R.id.imageButton2)
        imageButton.setOnClickListener{

            for (name in educationList){
                if(name.image == image){
                    Toast.makeText(this@ToyDetails, "Toy Already Aded!!!", Toast.LENGTH_SHORT).show()
                    test = 1
                }

            }
            if (test==0){
                AppDataBase.getDatabase(this).toydao().insert(
                    Toy(0,id,name,description,"",Price,image,"false","")

                )
                test=1
                Toast.makeText(this@ToyDetails, "Toy  Aded", Toast.LENGTH_SHORT).show()
            }




        }
    }
    var btnDemand: Button? = null

    private fun initView() {
        btnDemand = findViewById(R.id.btnDemand)


        var intent = intent
         id = intent.getStringExtra("_id").toString()
         name = intent.getStringExtra("Name").toString()
         image = intent.getStringExtra("image").toString()
         description = intent.getStringExtra("description").toString()
         Price = intent.getStringExtra("Price").toString()
        Owner = intent.getStringExtra("owner").toString()


        var txtDtId = findViewById<TextView>(R.id.nametv)
        var dImage=findViewById<ImageView>(R.id.imageView)
        var txtDtprice = findViewById<TextView>(R.id.pricetv)
        var txtDtdesc = findViewById<TextView>(R.id.desctv)


        txtDtId.text = name.toString()
        txtDtprice.text = Price.toString()
        txtDtdesc.text = description.toString()
        txtDtdesc.isEnabled = false
        txtDtId.isEnabled = false
        txtDtprice.isEnabled = false

        Glide.with(dImage).load(ApiService.BASE_URL + image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(dImage)


        btnDemand!!.setOnClickListener {
            val mainIntent = Intent(this, SwapDemand::class.java)

            mainIntent.putExtra("image", image)
            mainIntent.putExtra("Price", Price)
            mainIntent.putExtra("_id", id)
            mainIntent.putExtra("owner", Owner)
            startActivity(mainIntent)
            
        }

    }
}