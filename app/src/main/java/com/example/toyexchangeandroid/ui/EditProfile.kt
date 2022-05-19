package com.example.toyexchangeandroid.ui

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.fileutil
import com.example.toyexchangeandroid.service.ApiService
import com.example.toyexchangeandroid.service.ToyService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class EditProfile : AppCompatActivity()  {

    private lateinit var txtUserName: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPhoneNumber: TextView
    var imagebutt: ImageView? = null
    var btnUpdate: Button? = null

    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client
    lateinit var image : ImageView
    lateinit var uri: Uri
    var f: fileutil = fileutil()

    //map releted
    var mapView: MapView? = null
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        //map releted
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@EditProfile)

        fetchLocation()

        initView()

    }

    private fun initView() {

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        imagebutt = findViewById(R.id.profilePic)
        btnUpdate = findViewById(R.id.btnUpdateProfile)

        txtEmail = findViewById(R.id.txtEmail)
        txtUserName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber)
        image = findViewById(R.id.profilePic)


        txtEmail.isEnabled = true
        txtPhoneNumber.isEnabled = true
        txtUserName.isEnabled = true

        //------------------
        val gson = Gson()
        val  us =  sharedPreferences.getString(myuser, "USER")

        nowuser = gson.fromJson(us, Client::class.java)
        print("///////////////////")
        print(nowuser)
        print("///////////////////")

        txtEmail.text = nowuser.email
        txtUserName.text = nowuser.userName
        txtPhoneNumber.text = nowuser.phoneNumber



        Glide.with(image).load(ApiService.BASE_URL + nowuser.image).placeholder(R.drawable.imageload).circleCrop()
            .error(R.drawable.default_user).into(image)

        imagebutt!!.setOnClickListener {
            val fintent = Intent(Intent.ACTION_GET_CONTENT)
            fintent.type = "image/jpeg"
            try {
                startActivityForResult(fintent, 100)
            } catch (e: ActivityNotFoundException) {
            }
        }

        uri = Uri.parse(nowuser.image)

        btnUpdate!!.setOnClickListener {

            checkAndRequestPermission()


            if (uri != Uri.parse(nowuser.image)) {

                val file = File(f.getPath(uri, this))
                val reqFile = RequestBody.create("Image/*".toMediaTypeOrNull(), file)
                var imagee = MultipartBody.Part.createFormData(
                    "Image",
                    file.getName(), reqFile
                )


                ApiService.CLIENT_SERVICE.updateClient(
                    imagee,
                    nowuser._id,
                    txtUserName!!.text.toString(),
                    txtEmail!!.text.toString(),
                    txtPhoneNumber!!.text.toString()

                ).enqueue(
                    object : Callback<Client> {
                        override fun onResponse(
                            call: Call<Client>,
                            response: Response<Client>
                        ) {
                            if (response.code() == 200) {

                                Toast.makeText(
                                    this@EditProfile,
                                    "Client  updated!!!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<Client>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )


            } else {

                ApiService.CLIENT_SERVICE.updateClientWithoutImage(
                    nowuser._id,
                    txtUserName!!.text.toString(),
                    txtEmail!!.text.toString(),
                    txtPhoneNumber!!.text.toString()

                ).enqueue(
                    object : Callback<Client> {
                        override fun onResponse(
                            call: Call<Client>,
                            response: Response<Client>
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(
                                    this@EditProfile,
                                    "Client  updated!!!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<Client>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )

            }
        }





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




    //---------------------map releted -------------------
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(applicationContext, currentLocation.latitude.toString() + "" +
                        currentLocation.longitude, Toast.LENGTH_SHORT).show()

                mapView = findViewById(R.id.myMap)
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.myMap) as
                       SupportMapFragment?)!!

                supportMapFragment.getMapAsync(OnMapReadyCallback { mapView })


            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    fetchLocation()
                }
        }
    }

     fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)
    }

}