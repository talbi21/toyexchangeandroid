package com.example.toyexchangeandroid.service

import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.models.Toy
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ToyService {

    data class ToyResponse(
        @SerializedName("toy")
        val toy: Toy
    )

    data class ToyBody(val Name: String, val Description: String,
                       val  Price:String,val Size: String, val Image: String,
                       val  Swapped:String,val OwnerId:String)

    @Multipart
    @FormUrlEncoded
    @POST("/Toy/add")
    fun addPost(@Part image: MultipartBody.Part,@Body toyBody: ToyBody): Call<ToyResponse>

    @GET("/Toy")
    fun getPosts(): Call<MutableList<Toy>>

    @GET("/Toy/me/{OwnerId}")
    fun getmyPosts( @Path("OwnerId")OwnerId : String): Call<MutableList<Toy>>

}