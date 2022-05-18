package com.example.toyexchangeandroid.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R

class ProfileToyDemandsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val ClientPic : ImageView
    val DemandType : TextView
    val DemandMadeBy : TextView

    init {
        ClientPic = itemView.findViewById<ImageView>(R.id.clientPic)
        DemandType = itemView.findViewById<TextView>(R.id.DemandType)
        DemandMadeBy = itemView.findViewById<TextView>(R.id.DemandMadeBy)

    }
}