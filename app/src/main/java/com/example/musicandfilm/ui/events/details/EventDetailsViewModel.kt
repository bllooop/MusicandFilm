package com.example.musicandfilm.ui.events.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.services.event.EventApiInterface
import com.example.musicandfilm.services.event.EventApiService
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