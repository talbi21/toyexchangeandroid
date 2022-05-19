package com.example.toyexchangeandroid.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.service.ApiService
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class QrScanner : AppCompatActivity() {

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource : CameraSource
    private lateinit var detector : BarcodeDetector
    lateinit var CameraSurfaceView: SurfaceView
    lateinit var TextScanResult: TextView
    lateinit var idswap:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        val myIntent = intent
        idswap = myIntent.getStringExtra("swapID").toString()

        CameraSurfaceView = findViewById(R.id.CameraSurfaceView)
        TextScanResult = findViewById(R.id.TextScanResult)

        if(ContextCompat.checkSelfPermission( this@QrScanner, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            askForCameraPermission()
        }else{
            stepUpControls()
        }

    }

    private fun stepUpControls(){
        detector = BarcodeDetector.Builder(this@QrScanner).build()
        cameraSource = CameraSource.Builder(this@QrScanner , detector).setAutoFocusEnabled(true).build()

        CameraSurfaceView.holder.addCallback(surfaceCallBack)

        detector.setProcessor(prosessor)

    }


    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(
            this@QrScanner,
            arrayOf(Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                stepUpControls()
            }else{
                Toast.makeText(applicationContext,"Permission denied",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private val surfaceCallBack = object : SurfaceHolder.Callback{
        @SuppressLint("MissingPermission")
        override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
            try {
                cameraSource.start(surfaceHolder)

            } catch (exception : Exception){
                Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_SHORT).show()
            }

        }

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {
            cameraSource.stop()
        }

    }


    private val prosessor = object : Detector.Processor<Barcode>{
        override fun release() {
            TODO("Not yet implemented")
        }

        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {

            if (detections != null && detections.detectedItems.isNotEmpty())     {
                val qrCode : SparseArray<Barcode> = detections.detectedItems
                val code = qrCode.valueAt(0)
                TextScanResult.text = code.displayValue

                ApiService.swapService.acceptSwap(idswap,"true")
                    .enqueue(object :Callback<Swap> {
                    override fun onResponse(call: Call<Swap>, response: Response<Swap>) {
                        Toast.makeText(applicationContext,"Swap confirmed ",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    override fun onFailure(call: Call<Swap>, t: Throwable) {
                        Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
            }else{
                TextScanResult.text = ""
            }

        }

    }



}