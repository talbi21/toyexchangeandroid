package com.example.toyexchangeandroid.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    public  val BASE_URL = "http://10.0.2.2:3000/"

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

    val swapService: SwapService by lazy {
        retrofit().create(SwapService::class.java)
    }

}