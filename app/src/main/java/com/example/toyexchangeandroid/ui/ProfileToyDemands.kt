package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.ProfileToyDemandsAdapter
import com.example.toyexchangeandroid.adapters.SwapAdapter
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileToyDemands() : AppCompatActivity() {

    var swapList : MutableList<Swap> = ArrayList()
    var clientsList : MutableList<Client> = ArrayList()

    lateinit var recylcerSwap: RecyclerView
    lateinit var recylcerSwapAdapter: ProfileToyDemandsAdapter
    lateinit var idToy:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_toy_demands)

        init()
    }

    private fun init ( ){
        val myIntent = intent
        idToy = myIntent.getStringExtra("toyId").toString()

        Log.d("idToy", idToy.toString())

        clientsList.clear()
        swapList.clear()
        getClients()

        recylcerSwap = findViewById(R.id.ProfileToyDemandsRecycleView)
        recylcerSwapAdapter = ProfileToyDemandsAdapter(swapList,clientsList)
        recylcerSwap.adapter = recylcerSwapAdapter
        recylcerSwap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)


    }

    private fun getClients(){
        ApiService.CLIENT_SERVICE.allClients().enqueue(object : Callback<MutableList<Client>> {
            override fun onResponse(call: Call<MutableList<Client>>, response: Response<MutableList<Client>>) {
                val client = response.body()

                if (client != null) {
                    clientsList = client
                    getSwaps(idToy)
                }
                Log.d("clients","///////////////////////////////")
                Log.d("clients",client.toString())
            }

            override fun onFailure(call: Call<MutableList<Client>>, t: Throwable) {
            }
        })
    }

    private fun getSwaps(id:String){

        ApiService.swapService.demandByToy(id).enqueue(object : Callback<MutableList<Swap>> {
            override fun onResponse(call: Call<MutableList<Swap>>, response: Response<MutableList<Swap>>) {
                val swap = response.body()

                if (swap != null) {

                    swapList = swap
                    recylcerSwapAdapter = ProfileToyDemandsAdapter(swapList,clientsList)
                    recylcerSwap.adapter = recylcerSwapAdapter
                }
                Log.d("swaps","///////////////////////////////")
                Log.d("swaps",swap.toString())
            }
            override fun onFailure(call: Call<MutableList<Swap>>, t: Throwable) {
            }
        })


    }

}