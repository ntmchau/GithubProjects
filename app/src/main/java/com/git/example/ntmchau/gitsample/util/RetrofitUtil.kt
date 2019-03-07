package com.git.example.ntmchau.gitsample.util

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun <T> Call<T>.await(): RepoResponse<T> = suspendCoroutine { continuation ->
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>?, t: Throwable) {
            Log.d("await", "onFailure=$t")
            continuation.resume(RepoResponse.create(t))
        }

        override fun onResponse(call: Call<T>?, response: Response<T>) {
            Log.d("await", "onResponse=$response")
            continuation.resume(RepoResponse.create(response))
        }
    })
}