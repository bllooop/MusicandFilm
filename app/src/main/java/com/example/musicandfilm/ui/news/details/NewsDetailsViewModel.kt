package com.example.musicandfilm.ui.news.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.models.news.NewsResponse
import com.example.musicandfilm.services.event.EventApiInterface
import com.example.musicandfilm.services.event.EventApiService
import com.example.musicandfilm.services.news.NewsApiInterface
import com.example.musicandfilm.services.news.NewsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsViewModel : ViewModel() {
    var news_detail = MutableLiveData<List<Items>>()

    init {
        news_detail= MutableLiveData()
    }
    fun getSingleNewsData(id: String): LiveData<List<Items>> {
        val apiService = NewsApiService.getInstance().create(NewsApiInterface::class.java)
        apiService.getNewsDetail(id).enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {}
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                news_detail.postValue(response.body()!!.response!!.items)
            }
        })
        return news_detail
    }

    fun getLiveDataObserver(): MutableLiveData<List<Items>> {
        return news_detail
    }
}