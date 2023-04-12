package com.example.musicandfilm.services.event

import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.models.events.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApiInterface {
    @GET("events/?fields=id,title,images,dates&actual_since=1677450862&categories=cinema,concert")
    fun getEventList(): Call<EventResponse>
    @GET ("events/{id}/?lang=&fields=id,dates,title,images,body_text,location,age_restriction&expand=location")
    fun getEventDetail(@Path("id") event_id: Int): Call<EventDetail>

}