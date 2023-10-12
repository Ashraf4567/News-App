package com.example.newsapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.model.ApiConstants
import com.example.newsapp.api.model.ApiManager
import com.example.newsapp.api.model.newsResponse.News
import com.example.newsapp.api.model.newsResponse.NewsResponse
import com.example.newsapp.api.model.sourcesResponse.Source
import com.example.newsapp.api.model.sourcesResponse.SourcesResponse
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


    fun getNewsSources(categoryDataClass: CategoryDataClass) {
        shouldShowLoading.postValue(true)
        // viewBinding.progressBar.isVisible = true
        viewModelScope.launch {
            try {
                val response =
                    ApiManager.getApis().getSources(ApiConstants.API_KEY, categoryDataClass.id)
                sourcesLiveData.postValue(response.sources)

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
                val newsResponse = ApiManager.getApis()
                    .getNews(sources = sourceId ?: "", pageSize = pageSize, page = page)
                newsLiveData.postValue(newsResponse.articles)

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