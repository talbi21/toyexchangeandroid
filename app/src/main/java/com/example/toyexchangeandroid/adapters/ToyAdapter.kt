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
import com.example.toyexchangeandroid.ui.ToyDetails


class ToyAdapter(var context: Context, val ToyList: MutableList<Toy>) :
    RecyclerView.Adapter<ToyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)

        return ToyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToyViewHolder, position: Int) {

        val toy = ToyList[position]

        Glide.with(holder.ToyPic).load(BASE_URL + toy.image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(holder.ToyPic)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, toy.Name, Toast.LENGTH_LONG).show()
            var intent = Intent(context, ToyDetails::class.java)

            intent.putExtra("image", toy.image)
            intent.putExtra("Name", toy.Name)
            intent.putExtra("description", toy.description)
            intent.putExtra("Price", toy.Price)
            intent.putExtra("_id", toy._id)
            intent.putExtra("owner", toy.OwnerId)

            context.startActivity(intent)

        }

    }

    override fun getItemCount() = ToyList.size

}
