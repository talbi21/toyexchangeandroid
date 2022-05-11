package com.example.toyexchangeandroid.ui.fragments

import android.os.Bundle
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
import com.example.toyexchangeandroid.util.AppDataBase



class LikedListFragment : Fragment() {
    lateinit var educationRecyclerView: RecyclerView
    lateinit var educationAdapter: ToyAdapter

    lateinit var educationList : MutableList<Toy>

    lateinit var dataBase: AppDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_liked_list, container, false)

        dataBase = AppDataBase.getDatabase(requireActivity())

        educationRecyclerView = rootView.findViewById(R.id.likedListRecycleView)

        educationList = dataBase.toydao().getAllEducations()

        educationAdapter = ToyAdapter(rootView.context,educationList)

        educationRecyclerView.adapter = educationAdapter
        educationRecyclerView.layoutManager = GridLayoutManager(context, 2)

        //educationRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)

        return rootView
    }

}