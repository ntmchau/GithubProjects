package com.git.example.ntmchau.gitsample.api

import com.git.example.ntmchau.gitsample.data.remote.RepoRemote
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String, @Query("page") pageOffset: Int, @Query("per_page") pageSize: Int): Call<List<RepoRemote>>
}