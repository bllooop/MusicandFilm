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
import com.example.musicandfilm.services.news.NewsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDetailsViewModel : ViewModel() {
    var news_detail = MutableLiveData<List<Items>>()
    val newsRespository = NewsRepository()

    init {
        news_detail= MutableLiveData()
    }

    fun getDetailNews(id: String){
        newsRespository.getSingleNewsData(id,news_detail)

    }

    fun getLiveDataObserver(): MutableLiveData<List<Items>> {
        return news_detail
    }
}