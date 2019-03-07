package com.git.example.ntmchau.gitsample.di

import android.text.TextUtils
import com.git.example.ntmchau.gitsample.api.gsonconverter.GsonUTCDateAdapter
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object ServiceGenerator {

    private val mRetrofitBuilder = Retrofit.Builder().addConverterFactory(
        GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(Date::class.java, GsonUTCDateAdapter())
                .create()
        )

    )

    private var mRetrofit: Retrofit? = null
    private val mLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val mHttpClientBuilder = OkHttpClient.Builder().addInterceptor(mLoggingInterceptor)
    private var mBaseUrl: String? = null

    fun setBaseUrl(baseUrl: String) {
        if (!TextUtils.equals(mBaseUrl, baseUrl)) {
            mBaseUrl = baseUrl
            mRetrofitBuilder.baseUrl(mBaseUrl!!)
            mRetrofitBuilder.client(mHttpClientBuilder.build())
            mRetrofit = mRetrofitBuilder.build()
        }
    }

    fun <S> createService(serviceClass: Class<S>) = mRetrofit!!.create(serviceClass)!!
}