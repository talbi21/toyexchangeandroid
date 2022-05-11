package com.example.toyexchangeandroid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Toy")
data class Toy(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    val _id: String,
    @ColumnInfo(name = "Name")
    @SerializedName("Name")
    val Name: String,
    @ColumnInfo(name = "Description")
    @SerializedName("Description")
    val description: String,
    @ColumnInfo(name = "Size")
    @SerializedName("Size")
    val Size: String,
    @ColumnInfo(name = "Price")
    @SerializedName("Price")
    val Price: String,
    @ColumnInfo(name = "Image")
    @SerializedName("Image")
    val image: String,
    @ColumnInfo(name = "Swapped")
    @SerializedName("Swapped")
    val Swapped: Boolean,
    @ColumnInfo(name = "OwnerId")
    @SerializedName("OwnerId")
    val OwnerId: String,



    )