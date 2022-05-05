package com.example.toyexchangeandroid.models
import com.google.gson.annotations.SerializedName
import java.util.*

data class Client(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("type")
    val type: String,
    )

data class Token(
    @SerializedName("token")
    val token: String,
)