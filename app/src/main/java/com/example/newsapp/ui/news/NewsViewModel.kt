package com.example.newsapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.api.model.ApiManager
import com.example.newsapp.api.model.newsResponse.News
import com.example.newsapp.api.model.newsResponse.NewsResponse
import com.example.newsapp.api.model.sourcesResponse.Source
import com.example.newsapp.api.model.sourcesResponse.SourcesResponse
import com.example.newsapp.ui.ViewError
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val shouldShowLoading = MutableLiveData<Boolean>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    val errorLiveData = MutableLiveData<ViewError>()


    fun getNewsSources() {
        shouldShowLoading.postValue(true)
        // viewBinding.progressBar.isVisible = true
        ApiManager.getApis()
            .getSources()
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    shouldShowLoading.postValue(false)
                    errorLiveData.postValue(
                        ViewError(
                            throwable = t
                        ) {
                            getNewsSources()
                        }
                    )
//                    handleError(t, OnTryAgainClickListener {
//                        getNewsSources()
//                    })

                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    shouldShowLoading.postValue(false)
                    if (response.isSuccessful) {
                        sourcesLiveData.postValue(response.body()?.sources)
                    } else {
                        val errorBodyJsonString = response.errorBody()?.string()
                        val response =
                            Gson().fromJson(errorBodyJsonString, SourcesResponse::class.java)
                        errorLiveData.postValue(
                            ViewError(
                                message = response.message
                            ) {
                                getNewsSources()
                            }
                        )
                    }

                }
            })
    }

    fun getNews(
        sourceId: String?,
        pageSize: Int,
        page: Int
    ) {

        shouldShowLoading.postValue(true)
        ApiManager.getApis().getNews(sources = sourceId ?: "", pageSize = pageSize, page = page)
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    shouldShowLoading.postValue(false)
                    errorLiveData.postValue(
                        ViewError(
                            throwable = t
                        ) {
                            getNews(sourceId, pageSize = pageSize, page = page)
                        }
                    )
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    shouldShowLoading.postValue(false)
                    if (response.isSuccessful) {
                        //show news
                        newsLiveData.postValue(response.body()?.articles)
                        return
                    }
                    val responseJsonError = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseJsonError, NewsResponse::class.java)
                    errorLiveData.postValue(
                        ViewError(
                            message = errorResponse.message
                        ) {
                            getNews(sourceId, pageSize = pageSize, page = page)
                        }
                    )
//
                }
            })
    }


}