package com.example.toyexchangeandroid.models

import com.google.gson.annotations.SerializedName

data class Toy(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("Name")
    val Name: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Size")
    val Size: String,
    @SerializedName("Price")
    val Price: String,
    @SerializedName("Image")
    val image: String,
    @SerializedName("Swapped")
    val Swapped: Boolean,
    @SerializedName("OwnerId")
    val OwnerId: String,



)
