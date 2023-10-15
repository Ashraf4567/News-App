package com.data.dataSource

import com.data.api.model.WebServices
import com.data.api.model.newsResponse.News
import com.example.newsapp.dataSource.NewsDataSource

class NewsOnlineDataSourceImpl(val webServices: WebServices) : NewsDataSource {
    override suspend fun getNews(sourceId: String): List<News?>? {
        val response = webServices.getNews(sources = sourceId)
        return response.articles
    }
}