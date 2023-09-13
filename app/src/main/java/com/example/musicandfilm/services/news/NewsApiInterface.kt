package com.example.musicandfilm.services.news

import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.models.news.NewsResponse
import com.example.musicandfilm.models.news.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("wall.get?owner_id=-108468&domain=kinopoisk&count=20&extended=1&v=5.131&access_token=")
    fun getNewsList(): Call<NewsResponse>

    @GET ("wall.getById?extended=1&v=5.131&access_token=")
    fun getNewsDetail(@Query("posts") id: String): Call<NewsResponse>
    @GET("wall.get?owner_id=-12256840&count=20&extended=1&v=5.131&access_token=")
    fun getNewsListMusic(): Call<NewsResponse>
}
