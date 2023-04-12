package com.example.musicandfilm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicandfilm.REPOSITORY
import com.example.musicandfilm.room.RecentHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsertingRoomViewModel: ViewModel() {
    fun insert(recentHistory: RecentHistory)=
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.insertRecentItem(recentHistory)
        }

}