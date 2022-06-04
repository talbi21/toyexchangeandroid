package com.example.toyexchangeandroid.adapters

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService.BASE_URL
import com.example.toyexchangeandroid.ui.*
import com.example.toyexchangeandroid.view_holder.ProfileToyViewHolder


class MyToysAdapter(var context: Context, val ToyList: MutableList<Toy>)  :
    RecyclerView.Adapter<ProfileToyViewHolder>()  {


    lateinit var sharedPreferences: SharedPreferences
    private val activity : myToys = context as myToys


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileToyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.liked_list_item, parent, false)

        sharedPreferences = context.getSharedPreferences(PREF_NAME,MODE_PRIVATE)



        return ProfileToyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileToyViewHolder, position: Int) {

        val toy = ToyList[position]

        Glide.with(holder.ToyPic).load(BASE_URL + toy.image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(holder.ToyPic)

        holder.ToyDescription.text = toy.description
        holder.ToyName.text = toy.Name



        holder.itemView.setOnClickListener {

            //var intent = Intent(context, SwapDemand::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            //context.startActivity(intent)
            sharedPreferences.edit().apply {
                putString("SwappedID", toy._id)
                putString("SwappedImage", toy.image)

            }.apply()
            activity.finishMe()

        }

    }

    override fun getItemCount() = ToyList.size


}

