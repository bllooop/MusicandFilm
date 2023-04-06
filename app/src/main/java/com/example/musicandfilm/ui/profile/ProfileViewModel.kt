package com.example.musicandfilm.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.musicandfilm.REPOSITORY
import com.example.musicandfilm.room.RecentHistory
import com.example.musicandfilm.room.RecentItemDatabase
import com.example.musicandfilm.room.RecentItemRepository

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    val context = application
    fun initDatabase(){
        val recentDao = RecentItemDatabase.getInstance(context).getRecentDao()
        REPOSITORY = RecentItemRepository(recentDao)
    }
    fun getAllRecents():LiveData<List<RecentHistory>>{
        return REPOSITORY.allRecentItems
    }
}