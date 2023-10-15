package com.data.dataSource

import com.data.api.model.WebServices
import com.data.api.model.sourcesResponse.Source
import com.example.newsapp.dataSource.SourcesDataSource

class SourcesOnlineDataSourceImpl(val webServices: WebServices, val categoryId: String) :
    SourcesDataSource {
    override suspend fun getSources(categoryId: String): List<Source?>? {
        val response = webServices.getSources(category = categoryId)
        return response.sources
    }
}