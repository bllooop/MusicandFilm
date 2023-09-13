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
    @GET("wall.get?owner_id=-12256840&count=20&extended=1&v=5.131&access_token=2300f98b2300f98b2300f98bc12013d013223002300f98b4718217f8b8176811f7b0136")
    fun getNewsListMusic(): Call<NewsResponse>
}
