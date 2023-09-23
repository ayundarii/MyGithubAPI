package com.dicoding.mygithubapi.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubapi.data.local.entity.FavoriteEntity
import com.dicoding.mygithubapi.data.remote.FavoriteRepository
import com.dicoding.mygithubapi.data.remote.response.FollowItem
import com.dicoding.mygithubapi.data.remote.response.GithubResponse
import com.dicoding.mygithubapi.data.remote.retrofit.ApiConfig
import com.dicoding.mygithubapi.ui.activity.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    private val _user = MutableLiveData<GithubResponse>()
    val user : LiveData<GithubResponse> = _user

    private val _followers = MutableLiveData<List<FollowItem>>()
    val followers : LiveData<List<FollowItem>> = _followers

    private val _followings = MutableLiveData<List<FollowItem>>()
    val followings : LiveData<List<FollowItem>> = _followings

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    val loadingDetail = MutableLiveData<Boolean>()
    val loadingFollower = MutableLiveData<Boolean>()
    val loadingFollowing = MutableLiveData<Boolean>()

    fun findGetUserData(pencarian: String) {
       loadingDetail.value = true
        val client = ApiConfig.getApiService().getUserDetail(pencarian)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                loadingDetail.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                loadingDetail.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun findGetUserFollowers(pencarian: String) {
        loadingFollower.value = true
        val client = ApiConfig.getApiService().getFollowers(pencarian)
        client.enqueue(object : Callback<List<FollowItem>> {
            override fun onResponse(
                call: Call<List<FollowItem>>,
                response: Response<List<FollowItem>>
            ) {
                loadingFollower.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = responseBody
                    }
                } else {
                    Log.e("DetailViewModelFollower", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowItem>>, t: Throwable) {
                loadingFollower.value = false
                Log.e("DetailViewModelFollower", "onFailure: ${t.message}")
            }
        })
    }

    fun findGetUserFollowing(pencarian: String) {
        loadingFollowing.value = true
        val client = ApiConfig.getApiService().getFollowing(pencarian)
        client.enqueue(object : Callback<List<FollowItem>> {
            override fun onResponse(
                call: Call<List<FollowItem>>,
                response: Response<List<FollowItem>>
            ) {
                loadingFollowing.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followings.value = responseBody
                    }
                } else {
                    Log.e("DetailViewMFollowing", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowItem>>, t: Throwable) {
                loadingFollowing.value = false
                Log.e("DetailViewMFollowing", "onFailure: ${t.message}")
            }
        })
    }

    fun getAllFavorited(): LiveData<List<FavoriteEntity>> = mFavoriteRepository.getAllFavorite()

    fun setIsFavorite(isFavorite: Boolean){
        _isFavorite.value = isFavorite
    }

    private fun insert(favorite: FavoriteEntity){
        setIsFavorite(true)
        mFavoriteRepository.insert(favorite)
    }

    private fun delete(favorite: FavoriteEntity){
        setIsFavorite(false)
        mFavoriteRepository.delete(favorite)
    }

    fun updateFavorite(favorited: FavoriteEntity, activity: DetailActivity){
        if( isFavorite.value != true ){
            insert(favorited)
            showNotification(activity, "User ditambahkan ke Favorite Anda")
        }else{
            delete(favorited)
            showNotification(activity, "User tidak lagi di Favorite Anda")
        }
    }

    private fun showNotification(activity: DetailActivity, text: String){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

}