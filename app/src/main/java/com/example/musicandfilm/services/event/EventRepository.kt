package com.example.musicandfilm.services.event

import androidx.lifecycle.MutableLiveData
import com.example.musicandfilm.models.events.Event
import com.example.musicandfilm.models.events.EventDetail
import com.example.musicandfilm.models.events.EventResponse
import com.example.musicandfilm.room.RecentItemDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository() {

    fun getEventData(time: String, eventss:MutableLiveData<List<Event>>){
        val apiService = EventApiService.getInstance().create(EventApiInterface::class.java)
        apiService.getEventList(time).enqueue(object : Callback<EventResponse> {
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {}
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if(response.code()==200) {
                    return eventss.postValue(response.body()!!.events)
                }
            }
        })
    }
    fun getSingleEventDetail(id: Int,event_detail: MutableLiveData<EventDetail> ){
        val apiService = EventApiService.getInstance().create(EventApiInterface::class.java)
        apiService.getEventDetail(id).enqueue(object : Callback<EventDetail> {
            override fun onFailure(call: Call<EventDetail>, t: Throwable) {}
            override fun onResponse(call: Call<EventDetail>, response: Response<EventDetail>) {
                if (response.code()==200) {
                    event_detail.postValue(response.body())
                }
            }
        })
    }
}