package com.git.example.ntmchau.gitsample.util

import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Suppress("unused")
sealed class RepoResponse<T> {
    companion object {
        fun <T> create(error: Throwable): Failure<T> {
            return when (error) {
                is SocketTimeoutException -> {
                    Failure(CLIENT_TIMEOUT, error)
                }

                is UnknownHostException -> {
                    Failure(NO_INTERNET_CONNECTION, error)
                }

                else -> {
                    Failure(UNKNOWN_ERROR, error)
                }
            }
        }

        fun <T> create(response: Response<T>): RepoResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                Success(response = body)
            } else {
                return Failure(response.code(), null)
            }
        }
    }
}

data class Success<T>(val response: T?) : RepoResponse<T>()

data class Failure<T>(val code: Int, val throwable: Throwable? = null) : RepoResponse<T>()