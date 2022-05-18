package com.example.toyexchangeandroid.service

import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.models.Toy
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface SwapService {

    data class SwapResponse(
        @SerializedName("swap")
        val swap: Swap
    )

    data class SwapBody(val idToy1: String, val IdToy2: String,val IdClient1 :String, val IdClient2:String,val SwapType:String,val Confirmed:String)


    @POST("/Swap/add")
    fun addSwapDemand(@Body postBody: SwapBody): Call<SwapResponse>

    @GET("/Swap/demandByClient/{idClient1}" )
    fun demandByClient1( @Path("idClient1") idClient1 : String): Call<MutableList<Swap>>


    @GET("/Swap/demandByToy/{idToy}" )
    fun demandByToy( @Path("idToy") idToy : String): Call<MutableList<Swap>>

    @DELETE("/Swap/delete/{swapID}")
    fun deleteSwap( @Path("swapID")swapID : String): Call<Swap>

}