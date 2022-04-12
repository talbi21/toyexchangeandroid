package com.example.toyexchangeandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.toyexchangeandroid.fragments.*

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val likedListFragment = LikedListFragment()
        val addFragment = AddFragment()
        val demandListFragment = DemandListFragment()
        val messageFragment = MessageFragment()

        makeCurrentFragment(homeFragment)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    // Respond to navigation item 1 click
                    makeCurrentFragment(homeFragment)
                    true
                }
                R.id.bookmark -> {
                    // Respond to navigation item 1 click
                    makeCurrentFragment(likedListFragment)
                    true
                }
                R.id.add -> {
                    // Respond to navigation item 1 click
                    makeCurrentFragment(addFragment)
                    true
                }
                R.id.demandlist -> {
                    // Respond to navigation item 1 click
                    makeCurrentFragment(demandListFragment)
                    true
                }
                R.id.message -> {
                    // Respond to navigation item 1 click
                    makeCurrentFragment(messageFragment)
                    true
                }

                else -> false
            }
        }

    }

    private fun makeCurrentFragment(fragment: Fragment)= supportFragmentManager.beginTransaction().apply{
        replace(R.id.container,fragment)
        commit()
    }

}