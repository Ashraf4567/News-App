package com.data.repository

import com.data.api.model.sourcesResponse.Source
import com.example.newsapp.dataSource.SourcesDataSource
import com.example.newsapp.repository.SoursesRepository.SourcesRepository

class SourcesRepositoryImpl(private val sourcesDataSource: SourcesDataSource) : SourcesRepository {
    override suspend fun getSources(categoryId: String): List<Source?>? {
        val sources = sourcesDataSource.getSources(categoryId)
        return sources
    }
}