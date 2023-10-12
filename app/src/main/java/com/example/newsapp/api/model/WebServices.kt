package com.example.newsapp.api.model

import com.example.newsapp.api.model.newsResponse.NewsResponse
import com.example.newsapp.api.model.sourcesResponse.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apikey") key: String = ApiConstants.API_KEY,
        @Query("category") category: String
    ): SourcesResponse


    @GET("v2/everything")
    suspend fun getNews(
        @Query("apikey") key: String = ApiConstants.API_KEY,
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): NewsResponse
}