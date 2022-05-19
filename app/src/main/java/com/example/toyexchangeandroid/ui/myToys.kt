package com.example.toyexchangeandroid.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.MyToysAdapter
import com.example.toyexchangeandroid.adapters.ProfileItemAdapter
import com.example.toyexchangeandroid.databinding.FragmentProfileBinding
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class myToys : AppCompatActivity() {

    lateinit var recylcerToy: RecyclerView
    lateinit var recylcerProfileItemAdapter: MyToysAdapter

    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser: Client
    lateinit var test: ImageView
    var toyList: MutableList<Toy> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.my_toys)
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val gson = Gson()
        val  us =  sharedPreferences.getString(myuser, "USER")

        nowuser = gson.fromJson(us,Client::class.java)
        print("///////////////////")
        print(nowuser)
        print("///////////////////")
        getmyToys(nowuser._id)








        recylcerProfileItemAdapter = MyToysAdapter(this,toyList)
        recylcerToy = findViewById(R.id.myListRecycleView)
        recylcerToy.adapter = recylcerProfileItemAdapter
        recylcerToy.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)





    }




    private fun getmyToys(id: String) {


        ApiService.toyService.getmyPosts(id).enqueue(object : Callback<MutableList<Toy>> {
            override fun onResponse(
                call: Call<MutableList<Toy>>,
                response: Response<MutableList<Toy>>
            ) {
                val toy = response.body()

                if (toy != null) {
                    toyList = toy
                    recylcerProfileItemAdapter = MyToysAdapter(this@myToys, toyList)
                    recylcerToy.adapter = recylcerProfileItemAdapter
                }
                Log.d("toys", toy.toString())
            }

            override fun onFailure(call: Call<MutableList<Toy>>, t: Throwable) {
            }
        })
    }
}
