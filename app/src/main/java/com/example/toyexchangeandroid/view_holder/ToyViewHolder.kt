package com.example.toyexchangeandroid.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R

class ToyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val ToyPic : ImageView
        val ImageTitle : TextView
        //val QuestionTitle: TextView = itemView.findViewById<TextView>(R.id.markk)

        init {
            ToyPic = itemView.findViewById<ImageView>(R.id.ToyPic)
            ImageTitle = itemView.findViewById<TextView>(R.id.imagetitle)
        }
}