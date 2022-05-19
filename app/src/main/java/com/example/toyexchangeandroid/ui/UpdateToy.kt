package com.example.toyexchangeandroid.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.fileutil
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ToyService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateToy : AppCompatActivity() {

    var txtName : TextInputEditText? = null
    var txtDescription: TextInputEditText? = null
    var txtSize: TextInputEditText? = null
    var txtPrice: TextInputEditText? = null
    var btnAdd: Button? = null
    var imagebutt: ImageView? = null

    lateinit var uri: Uri
    var f: fileutil = fileutil()

    lateinit var idToy:String
    lateinit var name:String
    lateinit var description:String
    lateinit var size:String
    lateinit var price:String
    lateinit var swapped:String
    lateinit var ownerId:String
    lateinit var image:String

    private var txtLayoutName: TextInputLayout? = null
    private var txtLayoutDescription: TextInputLayout? = null
    private var txtLayoutSize: TextInputLayout? = null
    private var txtLayoutPrice: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_toy)


        txtName = findViewById(R.id.txtName)
        txtDescription = findViewById(R.id.txtDescription)
        txtSize = findViewById(R.id.txtSize)
        txtPrice = findViewById(R.id.txtPrice)
        btnAdd = findViewById(R.id.btnAddToy)
        imagebutt = findViewById(R.id.ToyPic)

        txtLayoutName = findViewById(R.id.txtLayoutName)
        txtLayoutDescription = findViewById(R.id.txtLayoutDescription)
        txtLayoutSize = findViewById(R.id.txtLayoutSize)
        txtLayoutPrice = findViewById(R.id.txtLayoutPrice)


        init()
        uri = Uri.parse(image)


        Glide.with(imagebutt!!).load(ApiService.BASE_URL + image).placeholder(R.drawable.imageload)
            .override(1000, 1000).error(R.drawable.notfoundd).into(imagebutt!!)

        txtName!!.setText(name)
        txtDescription!!.setText(description)
        txtSize!!.setText(size)
        txtPrice!!.setText(price)

        
        imagebutt!!.setOnClickListener {
            val fintent = Intent(Intent.ACTION_GET_CONTENT)
            fintent.type = "image/jpeg"
            try {
                startActivityForResult(fintent, 100)
            } catch (e: ActivityNotFoundException) {
            }
        }


        btnAdd!!.setOnClickListener {

            txtLayoutName!!.error = null
            txtLayoutDescription!!.error = null
            txtLayoutSize!!.error = null
            txtLayoutPrice!!.error = null

            if (txtName?.text!!.isEmpty()) {
                txtLayoutName!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtDescription?.text!!.isEmpty()) {
                txtLayoutDescription!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtSize?.text!!.isEmpty()) {
                txtLayoutSize!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtPrice?.text!!.isEmpty()) {
                txtLayoutPrice!!.error = "Check your email !"

                return@setOnClickListener
            }

            checkAndRequestPermission()


            if(uri != Uri.parse(image) ){

                val file = File(f.getPath(uri, this))
                val reqFile = RequestBody.create("Image/*".toMediaTypeOrNull(), file)
                var imagee = MultipartBody.Part.createFormData(
                    "Image",
                    file.getName(), reqFile
                )


                ApiService.toyService.updatePost(
                    imagee,
                    idToy,
                    txtName!!.text.toString() ,
                    txtDescription!!.text.toString(),
                    txtPrice!!.text.toString(),
                    txtSize!!.text.toString(),
                    swapped,
                    "1",
                    ownerId

                ).enqueue(
                    object : Callback<ToyService.ToyResponse> {
                        override fun onResponse(
                            call: Call<ToyService.ToyResponse>,
                            response: Response<ToyService.ToyResponse>
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(this@UpdateToy, "Toy  updated!!!", Toast.LENGTH_SHORT).show()

                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<ToyService.ToyResponse>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )


            }else{

                ApiService.toyService.updatePostWithoutImage(
                    idToy,
                    txtName!!.text.toString() ,
                    txtDescription!!.text.toString(),
                    txtPrice!!.text.toString(),
                    txtSize!!.text.toString(),
                    swapped,
                    "1",
                    ownerId

                ).enqueue(
                    object : Callback<ToyService.ToyResponse> {
                        override fun onResponse(
                            call: Call<ToyService.ToyResponse>,
                            response: Response<ToyService.ToyResponse>
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(this@UpdateToy, "Toy  updated!!!", Toast.LENGTH_SHORT).show()

                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<ToyService.ToyResponse>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )

            }



        }


    }


    fun init(){
        val myIntent = intent
        idToy = myIntent.getStringExtra("_id").toString()
        name = myIntent.getStringExtra("Name").toString()
        description = myIntent.getStringExtra("Description").toString()
        size = myIntent.getStringExtra("Size").toString()
        price = myIntent.getStringExtra("Price").toString()
        swapped = myIntent.getStringExtra("Swapped").toString()
        ownerId = myIntent.getStringExtra("OwnerId").toString()
        image = myIntent.getStringExtra("image").toString()



    }

        private val apppermissions = arrayOf<String>(

            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (data == null) return
            when (requestCode) {
                100 -> if (resultCode == RESULT_OK) {
                    uri = data.data!!
                    imagebutt!!.setImageURI(data.data)

                }
            }
        }

        private fun checkAndRequestPermission(): Boolean {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            for (perm in apppermissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        perm
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    listPermissionsNeeded.add(perm)
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this, listPermissionsNeeded.toTypedArray(),
                    200
                )
                return false
            }
            return true
        }


}