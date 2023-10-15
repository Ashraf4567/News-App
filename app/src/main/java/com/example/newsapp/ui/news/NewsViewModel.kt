package com.example.newsapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.api.model.ApiManager
import com.data.api.model.newsResponse.News
import com.data.api.model.newsResponse.NewsResponse
import com.data.api.model.sourcesResponse.Source
import com.data.api.model.sourcesResponse.SourcesResponse
import com.data.dataSource.NewsOnlineDataSourceImpl
import com.data.dataSource.SourcesOnlineDataSourceImpl
import com.data.repository.NewsRepositoryImpl
import com.data.repository.SourcesRepositoryImpl
import com.example.newsapp.dataSource.NewsDataSource
import com.example.newsapp.dataSource.SourcesDataSource
import com.example.newsapp.repository.SoursesRepository.SourcesRepository
import com.example.newsapp.repository.news.NewsRepository
import com.example.newsapp.ui.ViewError
import com.example.newsapp.ui.categories.CategoryDataClass
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {

    val shouldShowLoading = MutableLiveData<Boolean>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    val errorLiveData = MutableLiveData<ViewError>()

    val sourcesOnlineDataSource: SourcesDataSource = SourcesOnlineDataSourceImpl(
        ApiManager.getApis(), categoryId = "health"
    )
    val sourcesRepo: SourcesRepository = SourcesRepositoryImpl(sourcesOnlineDataSource)

    val newsDataSource: NewsDataSource = NewsOnlineDataSourceImpl(
        ApiManager.getApis()
    )
    val newsRepository: NewsRepository = NewsRepositoryImpl(newsDataSource)


    fun getNewsSources(categoryDataClass: CategoryDataClass) {
        shouldShowLoading.postValue(true)
        // viewBinding.progressBar.isVisible = true
        viewModelScope.launch {
            try {
                val sources = sourcesRepo.getSources(categoryDataClass.id)
                sourcesLiveData.postValue(sources)

            } catch (e: HttpException) {
                val errorBodyJsonString = e.response()?.errorBody()?.string()
                val response =
                    Gson().fromJson(errorBodyJsonString, SourcesResponse::class.java)
                errorLiveData.postValue(
                    ViewError(
                        message = response.message
                    ) {
                        getNewsSources(categoryDataClass)
                    }
                )
            } catch (e: Exception) {
                shouldShowLoading.postValue(false)
                errorLiveData.postValue(
                    ViewError(
                        throwable = e
                    ) {
                        getNewsSources(categoryDataClass)
                    }
                )
            } finally {
                shouldShowLoading.postValue(false)
            }
        }
    }

    fun getNews(
        sourceId: String?,
        pageSize: Int,
        page: Int
    ) {

        shouldShowLoading.postValue(true)

        viewModelScope.launch {
            try {
                val articles = newsRepository.getNews(sourceId ?: "")
                newsLiveData.postValue(articles)

            } catch (ex: HttpException) {
                val responseJsonError = ex.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(responseJsonError, NewsResponse::class.java)
                errorLiveData.postValue(
                    ViewError(
                        message = errorResponse.message
                    ) {
                        getNews(sourceId, pageSize = pageSize, page = page)
                    }
                )
            } catch (e: Exception) {

                errorLiveData.postValue(
                    ViewError(
                        throwable = e
                    ) {
                        getNews(sourceId, pageSize = pageSize, page = page)
                    }
                )
            } finally {
                shouldShowLoading.postValue(false)

            }
        }

    }


}