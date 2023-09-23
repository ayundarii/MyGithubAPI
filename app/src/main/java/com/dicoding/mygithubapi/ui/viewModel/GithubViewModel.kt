package com.dicoding.mygithubapi.ui.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.mygithubapi.data.remote.response.ResponseSearch
import com.dicoding.mygithubapi.data.remote.response.SearchUserData
import com.dicoding.mygithubapi.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel() : ViewModel() {
    private val _users = MutableLiveData<List<SearchUserData?>?>()
    val users : MutableLiveData<List<SearchUserData?>?> = _users

    val loadingPencarian = MutableLiveData<Boolean>()

    init {
        findUser("Ayu")
    }

    fun findUser(pencarian: String) {
        loadingPencarian.value = true
        val client = ApiConfig.getApiService().getUser(pencarian)
        client.enqueue(object : Callback<ResponseSearch> {
            override fun onResponse(
                call: Call<ResponseSearch>,
                response: Response<ResponseSearch>
            ) {
                loadingPencarian.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _users.value = responseBody.items
                    }
                } else {
                    Log.e("GithubViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                loadingPencarian.value = false
                Log.e("GithubViewModel", "onFailure: ${t.message}")
            }
        })
    }
}