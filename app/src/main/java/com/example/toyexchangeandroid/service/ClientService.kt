package com.example.toyexchangeandroid.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.toyexchangeandroid.models.Client

interface ClientService {

    data class ClientResponse(
        @SerializedName("client")
        val client: Client
    )

    data class LoginBody(val email: String, val password: String)
    data class ClientBody(val userName: String, val email: String, val password: String, val phoneNumber: String)

    @POST("/Client/loginClient")
    fun login(@Body loginBody: LoginBody): Call<Client>

    @POST("/Client/add")
    fun register(@Body clientBody: ClientBody): Call<ClientResponse>


}