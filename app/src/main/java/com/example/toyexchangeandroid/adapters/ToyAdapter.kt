package com.example.toyexchangeandroid.adapters;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.view_holder.ToyViewHolder
import com.example.toyexchangeandroid.service.ApiService.BASE_URL

class ToyAdapter(val ToyList: MutableList<Toy>) : RecyclerView.Adapter<ToyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)

        return ToyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToyViewHolder, position: Int) {

        val toy = ToyList[position]
       // val image = ToyList[position].image
        Glide.with(holder.ToyPic).load(BASE_URL+toy.image).placeholder(R.drawable.imageload).override(1000,1000).error(R.drawable.notfoundd).into(holder.ToyPic)


       // Log.d("image",toy.image)
       // val title = ToyList[position].Name

        //holder.QuestionName.text = name
        holder.ImageTitle.text = toy.Name

    }

    override fun getItemCount() = ToyList.size

}
