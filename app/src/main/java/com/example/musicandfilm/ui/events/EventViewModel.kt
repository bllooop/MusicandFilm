package com.example.musicandfilm.ui.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicandfilm.models.events.Event
import com.example.musicandfilm.models.events.EventResponse
import com.example.musicandfilm.services.event.EventApiInterface
import com.example.musicandfilm.services.event.EventApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventViewModel : ViewModel() {
    var eventss = MutableLiveData<List<Event>>()
    fun getLiveDataObserver():MutableLiveData<List<Event>>{
        return eventss
    }

    //callback: (List<Movie>) -> Unit
    fun getEventData(){
        val apiService = EventApiService.getInstance().create(EventApiInterface::class.java)
        apiService.getEventList().enqueue(object : Callback<EventResponse> {
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {}
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                return eventss.postValue(response.body()!!.events)
            }
        })
    }

}