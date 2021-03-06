package com.example.toyexchangeandroid.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import com.example.toyexchangeandroid.models.Client
import com.example.toyexchangeandroid.models.Swap
import com.example.toyexchangeandroid.models.Token
import okhttp3.MultipartBody
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


    @Multipart
    @POST("/Client/update/{ID}/{userName}/{email}/{phoneNumber}")
    fun updateClient(@Part image: MultipartBody.Part,@Path("ID")ID : String,@Path("userName")userName : String,
                     @Path("email")email : String,@Path("phoneNumber")phoneNumber : String): Call<Client>


    @POST("/Client/updatewithoutimage/{ID}/{userName}/{email}/{phoneNumber}")
    fun updateClientWithoutImage(@Path("ID")ID : String,@Path("userName")userName : String,
                               @Path("email")email : String,@Path("phoneNumber")phoneNumber : String): Call<Client>

    data class updatePassBody(val oldPassword: String, val newPassword: String)

    @POST("/Client/updatePassword/{ID}")
    fun updatePassword(@Path("ID")ID : String,@Body updatePassBody: updatePassBody): Call<ClientResponse>




}