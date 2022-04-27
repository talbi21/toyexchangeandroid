package com.example.toyexchangeandroid.ui.fragments

import android.os.Bundle
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
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
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
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        recylcerToy = rootView.findViewById(R.id.userToysRecycleView)

        //toyList.add(Toy("61a0393fbed7e02acad09b7c","bear","brown coton bear","big","3099","uploads/1644420878782Image.jpeg",true,0,"wajdi"))
        doLogin()

        recylcerToyAdapter = ToyAdapter(toyList)
        recylcerToy.adapter = recylcerToyAdapter

        recylcerToy.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)

        return rootView
    }

    private fun doLogin(){
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