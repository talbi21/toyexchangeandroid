package com.example.toyexchangeandroid.adapters

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.view_holder.SwapViewHolder

class SwapAdapter (val SwapList: MutableList<Swap>,val ToyList: MutableList<Toy>, val UserId: String) : RecyclerView.Adapter<SwapViewHolder>()  {

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwapViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.demand_list_item, parent, false)

        return SwapViewHolder(view)
    }

    override fun onBindViewHolder(holder: SwapViewHolder, position: Int) {

        if (UserId == SwapList[position].IdClient1) {
            for (toy in ToyList ) {
                if (SwapList[position].IdToy1 == toy._id){

                    Glide.with(holder.ToyPicClient1).load(ApiService.BASE_URL + toy.image).placeholder(R.drawable.imageload)
                        .override(1000, 1000).error(R.drawable.notfoundd).into(holder.ToyPicClient1)

                    //show toy image for client1
                }else if (SwapList[position].IdToy2 == toy._id){
                    //show toy image for client 2

                    Glide.with(holder.ToyPicClient2).load(ApiService.BASE_URL + toy.image).placeholder(R.drawable.imageload)
                        .override(1000, 1000).error(R.drawable.notfoundd).into(holder.ToyPicClient2)

                    val client2ToyName = toy.Name
                    val client2SwapType = SwapList[position].SwapType

                    holder.SwapDescription.text = "Demand to "+client2SwapType+ " , "+ client2ToyName
                }
            }
        

        }


    }

    override fun getItemCount() = SwapList.size

}