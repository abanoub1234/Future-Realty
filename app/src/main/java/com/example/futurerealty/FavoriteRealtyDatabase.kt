package com.example.futurerealty

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteRealtyData::class], version = 1)
abstract class FavoriteRealtyDatabase : RoomDatabase() {
    abstract fun favoriteRealtyDao(): FavoriteRealtyDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRealtyDatabase? = null

        fun getDatabase(context: Context): FavoriteRealtyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteRealtyDatabase::class.java,
                    "favorite_realty_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
