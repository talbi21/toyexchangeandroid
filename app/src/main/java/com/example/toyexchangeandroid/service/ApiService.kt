package com.example.toyexchangeandroid.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "http://192.168.1.123:5000/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }



    val userService: UserService by lazy {
        retrofit().create(UserService::class.java)
    }
}