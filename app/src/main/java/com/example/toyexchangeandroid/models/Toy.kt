package com.example.toyexchangeandroid.models

import com.google.gson.annotations.SerializedName

data class Toy(
    val _id: String,
    val Name: String,
    val description: String,
    val Size: String,
    val Price: String,
    val image: String,
    val Swapped: Boolean,
    val Rating: Int,
    val OwnerId: String,



    )
