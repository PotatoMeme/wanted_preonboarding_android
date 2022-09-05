package com.potatomeme.newsapp.api

import com.potatomeme.newsapp.gson.NewsResponse
import com.potatomeme.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    fun getTopNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET("v2/top-headlines")
    fun getCategoryNews(
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        categoryCode: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Call<NewsResponse>


}