package com.example.toyexchangeandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.view_holder.ProfileToyDemandsViewHolder

class ProfileToyDemandsAdapter (val SwapList: MutableList<Swap>,val ClientList: MutableList<Client>) : RecyclerView.Adapter<ProfileToyDemandsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileToyDemandsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_toy_demands_item, parent, false)

        return ProfileToyDemandsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileToyDemandsViewHolder, position: Int) {
        val swap = SwapList[position]

        for (client in ClientList ) {
            if (client._id == swap.IdClient1){

                Glide.with(holder.ClientPic).load(ApiService.BASE_URL + client.image).placeholder(R.drawable.imageload)
                    .override(1000, 1000).error(R.drawable.notfoundd).into(holder.ClientPic)

                holder.DemandMadeBy.text = client.userName
                holder.DemandType.text = swap.SwapType
            }
        }

    }

    override fun getItemCount() = SwapList.size



}