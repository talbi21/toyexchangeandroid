package com.example.toyexchangeandroid.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "https://toysexchange.herokuapp.com/"

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