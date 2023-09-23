package com.dicoding.mygithubapi.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.mygithubapi.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_users ORDER BY username DESC")
    fun getFavorites(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)
}