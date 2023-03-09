package com.example.musicandfilm.services

import com.example.musicandfilm.models.EventDetail
import com.example.musicandfilm.models.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiInterface {
    @GET("events/?fields=id,title,images,dates&location=msk&actual_since=1677450862")
    fun getEventList(): Call<EventResponse>
    @GET ("events/{id}/?lang=&fields=id,dates,title,images,body_text,location,age_restriction&expand=location")
    fun getEventDetail(@Path("id") event_id: Int): Call<EventDetail>

}