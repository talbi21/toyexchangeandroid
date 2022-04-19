package com.example.toyexchangeandroid.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.ToyAdapter
import com.example.toyexchangeandroid.databinding.FragmentHomeBinding
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService

import com.example.toyexchangeandroid.service.ToyService
import com.example.toyexchangeandroid.ui.Profile
import com.example.toyexchangeandroid.ui.Settings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    var toyList : MutableList<Toy> = ArrayList()
    override fun onResume() {
        super.onResume()
       ApiService.toyService.getPosts()
            .enqueue(
                object : Callback<ToyService.ToysResponse> {
                    override fun onResponse(

                        call: Call<ToyService.ToysResponse>,
                        response: Response<ToyService.ToysResponse>
                    ) {
                        if (response.code() == 200) {

                            toyList = response.body()?.Toys as MutableList<Toy>
                            recyclerView.adapter =
                                ToyAdapter(toyList)
                            Log.d("ttt","$toyList")
                        } else {
                            Log.d("HTTP ERROR", "status code is " + response.code())
                        }
                    }

                    override fun onFailure(
                        call: Call<ToyService.ToysResponse>,
                        t: Throwable
                    ) {
                        Log.d("FAIL", "faiiiiil"+toyList)
                    }

                }
            )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        recyclerView = view.findViewById(R.id.userToysRecycleView)

        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL ,false)

        recyclerView.adapter = ToyAdapter(toyList)








        return view
    }


}