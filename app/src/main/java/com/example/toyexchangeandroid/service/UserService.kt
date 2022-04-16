package com.example.toyexchangeandroid.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.toyexchangeandroid.models.User

interface UserService {

    data class UserResponse(
        @SerializedName("user")
        val user: User
    )

    data class LoginBody(val email: String, val password: String)
    data class UserBody( val userName: String,val email: String, val password: String, val phoneNumber: String)

    @POST("/Client/loginClient")
    fun login(@Body loginBody: LoginBody): Call<UserResponse>

    @POST("/Client/add")
    fun register(@Body userBody: UserBody): Call<UserResponse>
}