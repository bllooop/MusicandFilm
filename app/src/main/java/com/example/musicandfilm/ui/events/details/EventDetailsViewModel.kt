package com.example.musicandfilm.ui.events.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.services.event.EventApiInterface
import com.example.musicandfilm.services.event.EventApiService
import com.example.musicandfilm.services.event.EventRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailsViewModel : ViewModel() {
    var event_detail = MutableLiveData<EventDetail>()
    val eventRepository = EventRepository()

    init {
        event_detail= MutableLiveData()
    }
    fun getLiveDataObserver(): MutableLiveData<EventDetail> {
        return event_detail
    }

    fun getEvent(id: Int){
        eventRepository.getSingleEventDetail(id,event_detail)
    }
}