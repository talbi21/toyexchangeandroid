package com.example.toyexchangeandroid.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R

class ProfileToyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val ToyPic: ImageView
    val ToyDescription : TextView
    val ToyName : TextView



    //val QuestionTitle: TextView = itemView.findViewById<TextView>(R.id.markk)

    init {
        ToyPic = itemView.findViewById<ImageView>(R.id.ToyPicc)
        ToyDescription = itemView.findViewById<TextView>(R.id.ToyDescription)
        ToyName = itemView.findViewById<TextView>(R.id.ToyName)

    }

}