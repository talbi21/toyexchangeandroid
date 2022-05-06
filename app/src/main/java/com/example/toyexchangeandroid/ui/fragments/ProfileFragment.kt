package com.example.toyexchangeandroid.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.adapters.ProfileItemAdapter
import com.example.toyexchangeandroid.databinding.FragmentHomeBinding
import com.example.toyexchangeandroid.databinding.FragmentProfileBinding
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Toy
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.ui.EditProfile
import com.example.toyexchangeandroid.ui.PREF_NAME
import com.example.toyexchangeandroid.ui.email
import com.example.toyexchangeandroid.ui.myuser
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var txtUserName: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPhoneNumber: TextView

    lateinit var recylcerToy: RecyclerView
    lateinit var recylcerProfileItemAdapter: ProfileItemAdapter


    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client
    lateinit var test : ImageView
    var toyList : MutableList<Toy> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //var rootView : View = inflater.inflate(R.layout.fragment_profile, container, false)
        val bind = FragmentProfileBinding.inflate(layoutInflater)

        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME,MODE_PRIVATE)

        txtEmail = bind.root.findViewById(R.id.Email)
        txtUserName = bind.root.findViewById(R.id.UserName)
        txtPhoneNumber = bind.root.findViewById(R.id.PhoneNumber)
        test = bind.root.findViewById(R.id.ProfilePic)

        txtEmail.isEnabled = false
        txtPhoneNumber.isEnabled = false
        txtUserName.isEnabled = false

        //------------------
        val gson = Gson()
        val  us =  sharedPreferences.getString(myuser, "USER")

        nowuser = gson.fromJson(us,Client::class.java)
        print("///////////////////")
        print(nowuser)
        print("///////////////////")

        txtEmail.text = nowuser.email
        txtUserName.text = nowuser.userName
        txtPhoneNumber.text = nowuser.phoneNumber

        Glide.with(test).load(ApiService.BASE_URL + nowuser.image).placeholder(R.drawable.imageload).circleCrop()
            .error(R.drawable.default_user).into(test)

        //----------------



        recylcerToy = bind.root.findViewById(R.id.mytoys)

        getmyToys(nowuser._id)

        recylcerProfileItemAdapter = ProfileItemAdapter(requireContext(),toyList)
        recylcerToy.adapter = recylcerProfileItemAdapter
        recylcerToy.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)


        bind.btnToEditProfile.setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(), EditProfile::class.java)
            startActivity(intent)
        }

        return bind.root
    }



    private fun getmyToys(id:String){
        ApiService.toyService.getmyPosts(id).enqueue(object : Callback<MutableList<Toy>> {
            override fun onResponse(call: Call<MutableList<Toy>>, response: Response<MutableList<Toy>>) {
                val toy = response.body()

                if (toy != null) {
                    toyList = toy
                    recylcerProfileItemAdapter = ProfileItemAdapter(context!!,toyList)
                    recylcerToy.adapter = recylcerProfileItemAdapter
                }
                Log.d("toys",toy.toString())
            }
            override fun onFailure(call: Call<MutableList<Toy>>, t: Throwable) {
            }
        })
    }

}