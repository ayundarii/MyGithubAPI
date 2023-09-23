package com.dicoding.mygithubapi.data.remote

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.mygithubapi.data.local.entity.FavoriteEntity
import com.dicoding.mygithubapi.data.local.room.FavoriteDao
import com.dicoding.mygithubapi.data.local.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

        init {
            val db = FavoriteRoomDatabase.getDatabase(application)
            mFavoriteDao = db.favoriteDao()
        }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getFavorites()

    fun insert(favorite: FavoriteEntity) {
        executorService.execute { mFavoriteDao.insertFavorite(favorite) }
    }

    fun delete(favorite: FavoriteEntity) {
        executorService.execute { mFavoriteDao.deleteFavorite(favorite) }
    }
}