package com.luna.searchbooks.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceBuilder {

    /**
    >>>> https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide

    GET /v2/search/web HTTP/1.1
    Host: dapi.kakao.com
    Authorization: KakaoAK {app_key}

     */

    // Base URL
    private const val URL = "https://dapi.kakao.com"

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logger)

    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }


}