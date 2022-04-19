package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.toyexchangeandroid.R

class VerificationCode : AppCompatActivity() {

    private var toResetPassword: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        toResetPassword = findViewById(R.id.btnSubmit)

        toResetPassword!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, ResetPassword::class.java)
            startActivity(mainIntent)
            finish()
        });

    }
}