package com.dicoding.mygithubapi.data.remote.retrofit

import com.dicoding.mygithubapi.BuildConfig
import com.dicoding.mygithubapi.data.remote.response.FollowItem
import com.dicoding.mygithubapi.data.remote.response.GithubResponse
import com.dicoding.mygithubapi.data.remote.response.ResponseSearch
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") search: String
    ): Call<ResponseSearch>

    @GET("users/{login}")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<GithubResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<FollowItem>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<FollowItem>>
}