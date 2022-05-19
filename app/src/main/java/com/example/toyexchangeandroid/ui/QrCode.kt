package com.example.toyexchangeandroid.ui

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Client
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QrCode : AppCompatActivity() {

    private lateinit var ivCODE : ImageView
    private lateinit var edata : EditText
    private lateinit var btngn : Button
    lateinit var nowuser: Client

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var swapId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        sharedPreferences = this.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val gson = Gson()
        val  us =  sharedPreferences.getString(myuser, "USER")

        nowuser = gson.fromJson(us,Client::class.java)

        ivCODE = findViewById(R.id.imageView)

        val myIntent = intent
        swapId = myIntent.getStringExtra("swapID").toString()

        val data = swapId

        val write = QRCodeWriter()
        try {
            val bitMatrix = write.encode(data, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {

                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            ivCODE.setImageBitmap(bmp)
        }catch (e: WriterException){
            e.printStackTrace()
        }



    }
}