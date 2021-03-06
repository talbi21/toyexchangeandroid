package com.example.toyexchangeandroid.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.DemandListSwipeGesture
import com.example.toyexchangeandroid.adapters.ProfileSwipeGesture
import com.example.toyexchangeandroid.adapters.SwapAdapter
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.ui.PREF_NAME
import com.example.toyexchangeandroid.ui.ProfileToyDemands
import com.example.toyexchangeandroid.ui.QrScanner
import com.example.toyexchangeandroid.ui.myuser
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DemandListFragment : Fragment() {

    lateinit var recylcerSwap: RecyclerView
    lateinit var recylcerSwapAdapter: SwapAdapter

    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client

    var toyList : MutableList<Toy> = ArrayList()
    var swapList : MutableList<Swap> = ArrayList()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_demand_list, container, false)
        recylcerSwap = rootView.findViewById(R.id.demandListRecycleView)

        init()

        return rootView
    }

    private fun init(){


        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val gson = Gson()
        val  us =  sharedPreferences.getString(myuser, "USER")
        nowuser = gson.fromJson(us, Client::class.java)

        getSwaps(nowuser._id)

        Log.d("swap count count", swapList.count().toString())
        Log.d("toy count count", toyList.count().toString())

        recylcerSwapAdapter = SwapAdapter(swapList,toyList,nowuser._id)

        //------ swipe gesture releted -----
        val swipegesture = object : DemandListSwipeGesture(this@DemandListFragment) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT ->{
                        deleteItem(swapList[viewHolder.adapterPosition]._id)
                    }
                    ItemTouchHelper.RIGHT ->{

                        val intent = Intent(this@DemandListFragment.requireContext(), QrScanner()::class.java)
                        intent.putExtra("swapID",swapList[viewHolder.adapterPosition]._id)
                        startActivity(intent)

                    }
                }

            }

        }

        val touchHelper = ItemTouchHelper(swipegesture)
        touchHelper.attachToRecyclerView(recylcerSwap)
        //---------------

        recylcerSwap.adapter = recylcerSwapAdapter
        recylcerSwap.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
    }

    private fun getToys(){
        ApiService.toyService.getPosts().enqueue(object : Callback<MutableList<Toy>> {
            override fun onResponse(call: Call<MutableList<Toy>>, response: Response<MutableList<Toy>>) {
                val toy = response.body()

                if (toy != null) {
                    toyList = toy
                    recylcerSwapAdapter = SwapAdapter(swapList,toyList,nowuser._id)
                    recylcerSwap.adapter = recylcerSwapAdapter
                }
                Log.d("toys",toy.toString())
            }
            override fun onFailure(call: Call<MutableList<Toy>>, t: Throwable) {
            }
        })
    }

    private fun getSwaps(id:String){
        ApiService.swapService.demandByClient1(id).enqueue(object : Callback<MutableList<Swap>> {
            override fun onResponse(call: Call<MutableList<Swap>>, response: Response<MutableList<Swap>>) {
                val swap = response.body()

                if (swap != null) {
                    swapList = swap
                    getToys()
                }
                Log.d("swaps","///////////////////////////////")
                Log.d("swaps",swap.toString())
            }
            override fun onFailure(call: Call<MutableList<Swap>>, t: Throwable) {
            }
        })
    }

    fun deleteItem (swapID:String) {

        ApiService.swapService.deleteSwap(
            swapID
        ).enqueue(
            object : Callback<Swap> {
                override fun onResponse(
                    call: Call<Swap>,
                    response: Response<Swap>
                ) {
                    if (response.code() == 200) {
                        init()
                        recylcerSwapAdapter = SwapAdapter(swapList,toyList,nowuser._id)
                        recylcerSwap.adapter = recylcerSwapAdapter
                        Log.d("HTTP DELETED SUCCESSS", "status code is " + response.code() + "DELETED SUCCESSFULLY")
                    } else {
                        Log.d("HTTP ERROR", "status code is " + response.code())
                    }
                }

                override fun onFailure(
                    call: Call<Swap>,
                    t: Throwable
                ) {
                    Log.d("FAIL", "fail")
                }
            }
        )

        Log.d("deleteItem Id",swapID)

    }

}