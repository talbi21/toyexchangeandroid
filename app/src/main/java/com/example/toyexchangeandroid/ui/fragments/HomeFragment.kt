package com.example.toyexchangeandroid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.ToyAdapter
import com.example.toyexchangeandroid.databinding.FragmentHomeBinding
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.ui.AddToyActivity
import com.example.toyexchangeandroid.ui.Settings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var recylcerToy: RecyclerView
    lateinit var recylcerToyAdapter: ToyAdapter

    var toyList : MutableList<Toy> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentHomeBinding.inflate(layoutInflater)

        bind.Tosettings.setOnClickListener{
            val intent = Intent(this@HomeFragment.requireContext(), Settings::class.java)
            startActivity(intent)
        }

        bind.ToAdd.setOnClickListener{
            val intent = Intent(this@HomeFragment.requireContext(), AddToyActivity::class.java)
            startActivity(intent)
        }


        recylcerToy = bind.root.findViewById(R.id.userToysRecycleView)

        getToys()

        recylcerToyAdapter = ToyAdapter(toyList)
        recylcerToy.adapter = recylcerToyAdapter
        recylcerToy.layoutManager = GridLayoutManager(context, 2)



        return bind.root
    }

    private fun getToys(){
        ApiService.toyService.getPosts().enqueue(object : Callback<MutableList<Toy>> {
            override fun onResponse(call: Call<MutableList<Toy>>, response: Response<MutableList<Toy>>) {
                val toy = response.body()

                if (toy != null) {
                    toyList = toy
                    recylcerToyAdapter = ToyAdapter(toyList)
                    recylcerToy.adapter = recylcerToyAdapter
                }
                Log.d("toys",toy.toString())
            }
            override fun onFailure(call: Call<MutableList<Toy>>, t: Throwable) {
            }
        })
    }

}