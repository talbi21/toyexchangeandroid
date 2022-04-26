package com.example.toyexchangeandroid.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.view_holder.ToyViewHolder

class ToyAdapter(val ToyList: MutableList<Toy>) : RecyclerView.Adapter<ToyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)

        return ToyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToyViewHolder, position: Int) {

        //val image = ToyList[position].
        val title = ToyList[position].Name

        //holder.QuestionName.text = name
        holder.ImageTitle.text = title

    }

    override fun getItemCount() = ToyList.size

}
