package com.example.toyexchangeandroid.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "https://192.168.1.141:5000/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }



    val CLIENT_SERVICE: ClientService by lazy {
        retrofit().create(ClientService::class.java)
    }

    val toyService: ToyService by lazy {
        retrofit().create(ToyService::class.java)
    }
}