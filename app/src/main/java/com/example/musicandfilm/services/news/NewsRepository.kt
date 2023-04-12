package com.example.musicandfilm.services.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.models.news.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository (){
    fun getNewsData(newss: MutableLiveData<List<Items>>) {
        val apiService = NewsApiService.getInstance().create(NewsApiInterface::class.java)
        apiService.getNewsList().enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {}
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                return newss.postValue(response.body()!!.response!!.items)
            }
        })
    }

    fun getSingleNewsData(id: String, news_detail: MutableLiveData<List<Items>> ) {
        val apiService = NewsApiService.getInstance().create(NewsApiInterface::class.java)
        apiService.getNewsDetail(id).enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {}
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                news_detail.postValue(response.body()!!.response!!.items)
            }
        })
    }
}