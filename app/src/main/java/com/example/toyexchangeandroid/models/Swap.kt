package com.example.toyexchangeandroid.models

import com.google.gson.annotations.SerializedName

data class Swap (

    @SerializedName("_id")
    val _id: String,
    @SerializedName("IdToy1")
    val IdToy1: String,
    @SerializedName("IdToy2")
    val IdToy2: String,
    @SerializedName("IdClient1")
    val IdClient1: String,
    @SerializedName("IdClient2")
    val IdClient2: String,
    @SerializedName("SwapType")
    val SwapType: String,
    @SerializedName("Confirmed")
    val Confirmed: String,

)