package com.example.toyexchangeandroid.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.ProfileItemAdapter
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Token
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ClientService
import com.example.toyexchangeandroid.service.SwapService
import com.example.toyexchangeandroid.ui.fragments.HomeFragment
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwapDemand : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_demand)

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)



        //------------------
        val gson = Gson()
        val  us =  sharedPreferences.getString(myuser, "USER")

        nowuser = gson.fromJson(us,Client::class.java)
        Log.d("///////////////////","///////////////////")
        Log.d("///////////////////", nowuser.toString())
        Log.d("///////////////////","///////////////////")

        var intent = intent
        var id = intent.getStringExtra("_id")
        var image = intent.getStringExtra("image")
        var Price = intent.getStringExtra("Price")
        var Owner = intent.getStringExtra("owner")


       Log.d("IDTOY", id!!)
      Log.d("imageTOY", image!!)




        var btnBuy = findViewById<Button>(R.id.btnAddBuy)
        var txtprice = findViewById<TextView>(R.id.ToyPrice)
        var dImage=findViewById<ImageView>(R.id.ToyPicClient1)
        var dImage2=findViewById<ImageView>(R.id.ToyPicClient2)

        dImage2.setOnClickListener {

            var intent2 = Intent(this, myToys::class.java)


            startActivity(intent2)

        }

        btnBuy.setOnClickListener{


            demandBuy(id!!,nowuser._id, Owner!!,"buy","false")

        }

        Glide.with(dImage).load(ApiService.BASE_URL + image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(dImage)
        txtprice.text= Price
    }



    fun demandBuy(idToy:String,IdClient1 :String,IdClient2:String, SwapType:String, Confirmed:String,){
        ApiService.swapService.addBuyDemand(SwapService.BuyBody(
            idToy,IdClient1,IdClient2,SwapType,Confirmed
        )).enqueue(object : Callback<SwapService.SwapResponse> {
            override fun onResponse(
                call: Call<SwapService.SwapResponse>,
                response: Response<SwapService.SwapResponse>
            ) {
                Toast.makeText(this@SwapDemand,"confirmed demand", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SwapDemand, MainActivity::class.java)
                startActivity(intent)
            }
            override fun onFailure(call: Call<SwapService.SwapResponse>, t: Throwable) {
//                Toast.makeText(this@SwapDemand,"erreur" + t.message, Toast.LENGTH_SHORT).show()
//                Log.d("Er",t.message.toString())
                Toast.makeText(this@SwapDemand,"confirmed demand", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SwapDemand, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

}