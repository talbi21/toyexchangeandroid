package com.example.toyexchangeandroid.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.view_holder.ToyViewHolder
import com.example.toyexchangeandroid.service.ApiService.BASE_URL
import com.example.toyexchangeandroid.ui.Details
import com.example.toyexchangeandroid.view_holder.ProfileToyViewHolder


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



//        holder.itemView.setOnClickListener({
//            Toast.makeText(context, toy.Name, Toast.LENGTH_LONG).show()
//            var intent = Intent(context, Details::class.java)
//
//            intent.putExtra("image", toy.image)
//            intent.putExtra("Name", toy.Name)
//            intent.putExtra("description", toy.description)
//            intent.putExtra("Price", toy.Price)
//            intent.putExtra("_id", toy._id)
//
//
//
//            context.startActivity(intent)
//
//
//        })

    }

    override fun getItemCount() = ToyList.size

}
