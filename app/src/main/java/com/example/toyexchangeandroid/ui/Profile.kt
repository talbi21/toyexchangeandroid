package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.toyexchangeandroid.R

class Profile : AppCompatActivity() {

    private var ToEditProfile: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ToEditProfile = findViewById(R.id.btnToEditProfile)

        ToEditProfile!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, EditProfile::class.java)
            startActivity(mainIntent)
            finish()
        });

    }
}