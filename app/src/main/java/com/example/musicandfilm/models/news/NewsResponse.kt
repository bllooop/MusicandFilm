package com.example.musicandfilm.models.news

import com.example.musicandfilm.models.news.Response
import com.google.gson.annotations.SerializedName


data class NewsResponse(

    @SerializedName("response" ) var response : Response? = Response()

)