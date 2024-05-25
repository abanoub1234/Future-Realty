package com.example.futurerealty
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteRealtyDao {

    @Insert
     fun insert(favoriteRealtyData: FavoriteRealtyData)

    @Query("SELECT * FROM favorites WHERE userId = :userId")
     fun getAllFavorites(userId: String): List<FavoriteRealtyData>
}
