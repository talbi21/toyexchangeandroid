package com.example.toyexchangeandroid.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R

class SwapViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val ToyPicClient1 : ImageView
    val ToyPicClient2 : ImageView
    val SwapDescription : TextView

    init {
        ToyPicClient1 = itemView.findViewById<ImageView>(R.id.ToyPicClient1)
        ToyPicClient2 = itemView.findViewById<ImageView>(R.id.ToyPicClient2)
        SwapDescription = itemView.findViewById<TextView>(R.id.SwapDescription)
    }

}