package com.example.toyexchangeandroid.dao

import androidx.room.*
import com.example.toyexchangeandroid.models.Toy

@Dao
interface ToyDao {
    @Insert
    fun insert(edc: Toy)

    @Update
    fun update(edc: Toy)

    @Delete
    fun delete(edc: Toy)

    @Query("SELECT * FROM Toy")
    fun getAllEducations(): MutableList<Toy>
}