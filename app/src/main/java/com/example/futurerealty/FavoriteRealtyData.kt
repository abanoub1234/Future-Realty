package com.example.futurerealty

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteRealtyData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val imageUrl: String,
    val price: String,
    val description: String,
    val location: String,
    val contact: String
)
