package com.example.toyexchangeandroid.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.models.Token
import retrofit2.http.*

interface ClientService {

    data class ClientResponse(
        @SerializedName("client")
        val client: Client
    )

    data class LoginBody(val email: String, val password: String)

    data class GetUserFromTokenBody(
        val token: String
    )
    data class ClientBody(val userName: String, val email: String, val password: String, val phoneNumber: String)

    @POST("/Client/loginClient")
    fun login(@Body loginBody: LoginBody): Call<Token>


    @POST("/Client/findToken")
    fun getUserFromToken(@Header("Authorization") getUserFromTokenBody: GetUserFromTokenBody): Call<Client>

    @POST("/Client/add")
    fun register(@Body clientBody: ClientBody): Call<ClientResponse>


    @GET("/Client/" )
    fun allClients(): Call< MutableList<Client> >


}