package com.example.toyexchangeandroid.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.view_holder.SwapViewHolder

class SwapAdapter (val SwapList: MutableList<Swap>,val ToyList: MutableList<Toy>) : RecyclerView.Adapter<SwapViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwapViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.demand_list_item, parent, false)

        return SwapViewHolder(view)
    }

    override fun onBindViewHolder(holder: SwapViewHolder, position: Int) {

            if ("6199c2d47da84e27902efd29" == SwapList[position].IdClient1) {
                for (toy in ToyList ) {
                    if (SwapList[position].IdToy1 == toy._id){

                        //show toy image for client1
                    }else if (SwapList[position].IdToy2 == toy._id){
                        //show toy image for client 2
                        val client2ToyName = ToyList[position].Name
                        val client2SwapType = SwapList[position].SwapType

                        holder.SwapDescription.text = "Demand to "+client2SwapType+ " , "+ client2ToyName
                    }
                }

            }

    }

    override fun getItemCount() = SwapList.size

}