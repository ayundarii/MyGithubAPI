package com.dicoding.mygithubapi.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubapi.data.local.entity.FavoriteEntity
import com.dicoding.mygithubapi.data.remote.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavoriteRepository.getAllFavorite()
}