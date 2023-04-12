package com.example.musicandfilm.ui.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.events.Event
import com.example.musicandfilm.models.events.EventResponse
import com.example.musicandfilm.services.event.EventApiInterface
import com.example.musicandfilm.services.event.EventApiService
import com.example.musicandfilm.services.event.EventRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventViewModel : ViewModel() {
    var eventss = MutableLiveData<List<Event>>()
    val eventRepository = EventRepository()
    fun getLiveDataObserver():MutableLiveData<List<Event>>{
        return eventss
    }

    fun getAllEvents(){
        eventRepository.getEventData(eventss)
    }
}
