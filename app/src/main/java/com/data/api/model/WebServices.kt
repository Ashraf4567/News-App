package com.data.api.model

import com.data.api.model.newsResponse.NewsResponse
import com.data.api.model.sourcesResponse.SourcesResponse
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
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): NewsResponse
}