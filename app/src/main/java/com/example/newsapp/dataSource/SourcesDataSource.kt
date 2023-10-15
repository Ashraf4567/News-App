package com.example.newsapp.dataSource

import com.data.api.model.sourcesResponse.Source

interface SourcesDataSource {
    suspend fun getSources(categoryId: String): List<Source?>?

}