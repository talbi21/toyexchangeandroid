package com.example.toyexchangeandroid.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.databinding.FragmentHomeBinding
import com.example.toyexchangeandroid.databinding.FragmentProfileBinding
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.ui.EditProfile
import com.example.toyexchangeandroid.ui.PREF_NAME
import com.example.toyexchangeandroid.ui.email
import com.example.toyexchangeandroid.ui.myuser
import com.google.gson.Gson

class ProfileFragment : Fragment() {

    private lateinit var txtUserName: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPhoneNumber: TextView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //var rootView : View = inflater.inflate(R.layout.fragment_profile, container, false)
        val bind = FragmentProfileBinding.inflate(layoutInflater)

        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME,MODE_PRIVATE)

        txtEmail = bind.root.findViewById(R.id.Email)
        txtEmail.isEnabled = false
        txtUserName = bind.root.findViewById(R.id.UserName)
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


        //----------------

        bind.btnToEditProfile.setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(), EditProfile::class.java)
            startActivity(intent)
        }

        return bind.root
    }

}