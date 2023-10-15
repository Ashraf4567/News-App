package com.data.repository

import com.data.api.model.newsResponse.News
import com.example.newsapp.dataSource.NewsDataSource
import com.example.newsapp.repository.news.NewsRepository

class NewsRepositoryImpl(val dataSource: NewsDataSource) : NewsRepository {
    override suspend fun getNews(sourceId: String): List<News?>? {
        return dataSource.getNews(sourceId)
    }
}