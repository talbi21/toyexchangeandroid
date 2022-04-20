package com.example.toyexchangeandroid.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.databinding.FragmentHomeBinding
import com.example.toyexchangeandroid.databinding.FragmentProfileBinding
import com.example.toyexchangeandroid.ui.EditProfile
import com.example.toyexchangeandroid.ui.email

class ProfileFragment : Fragment() {

    private lateinit var txtUserName: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPhoneNumber: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //var rootView : View = inflater.inflate(R.layout.fragment_profile, container, false)
        val bind = FragmentProfileBinding.inflate(layoutInflater)

        //txtEmail = rootView.findViewById(R.id.Email)
        //txtEmail.isEnabled = false
        //txtEmail.text = requireArguments().getString(email,"NULL")


        bind.btnToEditProfile.setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(), EditProfile::class.java)
            startActivity(intent)
        }

        return bind.root
    }

}