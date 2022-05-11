package com.example.toyexchangeandroid.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.toyexchangeandroid.dao.ToyDao
import com.example.toyexchangeandroid.models.Toy

@Database(entities = [Toy::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun toydao(): ToyDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDataBase::class.java, "toyList")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return instance!!
        }
    }
}
