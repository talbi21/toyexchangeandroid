package com.example.toyexchangeandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView



class Login : AppCompatActivity() {
    private var btnSubmit: Button? = null
    private var toSignUp: TextView? = null
    private var toForgotPassword: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSubmit = findViewById(R.id.btnLogin)
        toSignUp = findViewById(R.id.ToSignUp)
        toForgotPassword = findViewById(R.id.ToForgotPassword)

        //onClick btn
        btnSubmit!!.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

        toSignUp!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, SignUp::class.java)
            startActivity(mainIntent)
            finish()
        });

        toForgotPassword!!.setOnClickListener( View.OnClickListener{
            val mainIntent = Intent(this, SignUp::class.java)
            startActivity(mainIntent)
            finish()
        });

    }
}