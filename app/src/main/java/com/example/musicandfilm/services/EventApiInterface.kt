package com.example.musicandfilm.services

import com.example.musicandfilm.models.EventResponse
import retrofit2.Call
import retrofit2.http.GET

interface EventApiInterface {
    @GET("events/?fields=id,title,images,dates&location=msk&actual_since=1677450862")
    fun getEventList(): Call<EventResponse>
}