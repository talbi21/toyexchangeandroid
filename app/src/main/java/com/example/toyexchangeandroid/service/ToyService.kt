package com.example.toyexchangeandroid.service

import com.example.toyexchangeandroid.models.Toy
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Body
import retrofit2.http.POST

interface ToyService {

    data class ToyResponse(
        @SerializedName("toy")
        val toy: Toy
    )
    data class ToyBody(val Name: String, val Description: String,
                       val  Price:String,val Size: String, val Image: String,
                       val  Swapped:String,val OwnerId:String)

    @POST("/Toy/add")
    fun addPost(@Body postBody: ToyBody): Call<ToyResponse>

    @GET("/Toy")
    fun getPosts(): Call<MutableList<Toy>>

}