package com.example.musicandfilm.services.event

import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.models.events.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApiInterface {
    @Headers("cookes: erhfuiwhgfiuwerhiw4ueghfwoqghuyq4go7w4gfuier")
    @GET("events/?fields=id,title,images,dates&categories=cinema,concert")
    fun getEventList(@Query("actual_since") time: String): Call<EventResponse>
    @GET ("events/{id}/?lang=&fields=id,dates,title,images,body_text,location,age_restriction&expand=location")
    fun getEventDetail(@Path("id") event_id: Int): Call<EventDetail>

}