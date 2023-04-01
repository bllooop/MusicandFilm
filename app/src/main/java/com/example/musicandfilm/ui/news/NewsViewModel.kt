package com.example.musicandfilm.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.models.news.NewsResponse
import com.example.musicandfilm.services.news.NewsApiInterface
import com.example.musicandfilm.services.news.NewsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    var newss = MutableLiveData<List<Items>>()

    fun getLiveDataObserver(): MutableLiveData<List<Items>> {
        return newss
    }

    fun getNewsData(){
        val apiService = NewsApiService.getInstance().create(NewsApiInterface::class.java)
        apiService.getNewsList().enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {}
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                return newss.postValue(response.body()!!.response!!.items)
            }
        })
    }
}
