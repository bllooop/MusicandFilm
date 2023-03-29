package com.example.musicandfilm.services

import com.example.musicandfilm.models.news.NewsResponse
import com.example.musicandfilm.models.news.Response
import retrofit2.Call
import retrofit2.http.GET

interface NewsApiInterface {
    @GET("wall.get?owner_id=-108468&domain=kinopoisk&count=10&extended=1&v=5.131&access_token=2300f98b2300f98b2300f98bc12013d013223002300f98b4718217f8b8176811f7b0136")
    fun getNewsList(): Call<NewsResponse>
}
