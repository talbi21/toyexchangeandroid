package com.example.toyexchangeandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.toyexchangeandroid.R

class SentVerificationEmail : AppCompatActivity() {

    private var toVerificationCode: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent_verification_email)

        toVerificationCode = findViewById(R.id.btnSubmit)

        toVerificationCode!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, VerificationCode::class.java)
            startActivity(mainIntent)
            finish()
        });
    }
}