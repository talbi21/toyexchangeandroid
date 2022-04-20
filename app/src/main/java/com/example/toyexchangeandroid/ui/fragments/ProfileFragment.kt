package com.example.toyexchangeandroid.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.databinding.FragmentHomeBinding
import com.example.toyexchangeandroid.databinding.FragmentProfileBinding
import com.example.toyexchangeandroid.ui.EditProfile

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        val bind = FragmentProfileBinding.inflate(layoutInflater)

        bind.btnToEditProfile.setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(), EditProfile::class.java)
            startActivity(intent)
        }

        return bind.root
    }

}