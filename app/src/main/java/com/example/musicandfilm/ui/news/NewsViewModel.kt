package com.example.musicandfilm.ui.news

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.news.Items
import com.example.musicandfilm.services.news.NewsRepository


class NewsViewModel : ViewModel() {
    var newss = MutableLiveData<List<Items>>()
    var news_film = MutableLiveData<List<Items>>()
    var news_music = MutableLiveData<List<Items>>()
    val newsRespository = NewsRepository()

    fun getLiveDataObserver(): MutableLiveData<List<Items>> {
        return newss
    }

    fun getAllNews(){
        newsRespository.getNewsData(newss)
    }
}