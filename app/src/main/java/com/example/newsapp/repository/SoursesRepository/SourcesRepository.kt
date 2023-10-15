package com.example.newsapp.repository.SoursesRepository

import com.data.api.model.sourcesResponse.Source

interface SourcesRepository {
    suspend fun getSources(categoryId: String): List<Source?>?

}