package com.dicoding.mygithubapi.data.remote.retrofit

import com.dicoding.mygithubapi.data.remote.response.FollowItem
import com.dicoding.mygithubapi.data.remote.response.GithubResponse
import com.dicoding.mygithubapi.data.remote.response.ResponseSearch
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(
        @Query("q") search: String
    ): Call<ResponseSearch>

    @Headers("Authorization: token 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")
    @GET("users/{login}")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<GithubResponse>

    @Headers("Authorization: token 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")
    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<FollowItem>>

    @Headers("Authorization: token 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")
    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<FollowItem>>
}