package com.example.toyexchangeandroid.ui

import android.content.SharedPreferences
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.service.ApiService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.gson.Gson
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EditProfile : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var txtUserName: TextView
    private lateinit var txtLocation: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtPhoneNumber: TextView

    lateinit var sharedPreferences: SharedPreferences
    lateinit var nowuser : Client
    lateinit var image : ImageView


    //map releted
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

        txtEmail = findViewById(R.id.txtEmail)
        txtUserName = findViewById(R.id.txtName)
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber)
        image = findViewById(R.id.imageView2)

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

    }

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
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.myMap) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@EditProfile)
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

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)
    }

}