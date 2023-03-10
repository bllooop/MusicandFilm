package com.example.musicandfilm.ui.events.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.EventDetail
import com.example.musicandfilm.models.MovieDetails
import com.example.musicandfilm.services.EventApiInterface
import com.example.musicandfilm.services.EventApiService
import com.example.musicandfilm.services.MovieApiInterface
import com.example.musicandfilm.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailsViewModel : ViewModel() {
    var event_detail = MutableLiveData<EventDetail>()

    init {
        event_detail= MutableLiveData()
    }
    fun getSingleEventDetail(id: Int): LiveData<EventDetail> {
        val apiService = EventApiService.getInstance().create(EventApiInterface::class.java)
        apiService.getEventDetail(id).enqueue(object : Callback<EventDetail> {
            override fun onFailure(call: Call<EventDetail>, t: Throwable) {}
            override fun onResponse(call: Call<EventDetail>, response: Response<EventDetail>) {
                event_detail.postValue(response.body())
            }
        })
        return event_detail
    }

    fun getLiveDataObserver(): MutableLiveData<EventDetail> {
        return event_detail
    }}