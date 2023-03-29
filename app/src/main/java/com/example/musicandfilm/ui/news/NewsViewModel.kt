package com.example.musicandfilm.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.models.news.NewsResponse
import com.example.musicandfilm.services.NewsApiInterface
import com.example.musicandfilm.services.NewsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    var newss = MutableLiveData<List<com.example.musicandfilm.models.news.Response>>()

    fun getLiveDataObserver(): MutableLiveData<List<com.example.musicandfilm.models.news.Response>> {
        return newss
    }

    fun getNewsData(){
        val apiService = NewsApiService.getInstance().create(NewsApiInterface::class.java)
        apiService.getNewsList().enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {}
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                return newss.postValue(response.body()!!.response)
            }
        })
    }
}
