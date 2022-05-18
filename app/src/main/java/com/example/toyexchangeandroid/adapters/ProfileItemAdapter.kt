package com.example.toyexchangeandroid.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ApiService.BASE_URL
import com.example.toyexchangeandroid.service.ToyService
import com.example.toyexchangeandroid.ui.ProfileToyDemands
import com.example.toyexchangeandroid.ui.UpdateToy
import com.example.toyexchangeandroid.view_holder.ProfileToyViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileItemAdapter(var context: Context, val ToyList: MutableList<Toy>) :
    RecyclerView.Adapter<ProfileToyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileToyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.liked_list_item, parent, false)

        return ProfileToyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileToyViewHolder, position: Int) {

        val toy = ToyList[position]

        Glide.with(holder.ToyPic).load(BASE_URL + toy.image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(holder.ToyPic)

        holder.ToyDescription.text = toy.description
        holder.ToyName.text = toy.Name


        holder.itemView.setOnClickListener {
            var intent = Intent(context, UpdateToy::class.java)
            intent.putExtra("_id", toy._id)
            intent.putExtra("Name", toy.Name)
            intent.putExtra("Description", toy.description)
            intent.putExtra("Size", toy.Size)
            intent.putExtra("Price", toy.Price)
            intent.putExtra("Swapped", toy.Swapped)
            intent.putExtra("OwnerId", toy.OwnerId)
            intent.putExtra("image", toy.image)
            context.startActivity(intent)
        }


    }

    override fun getItemCount() = ToyList.size





}
