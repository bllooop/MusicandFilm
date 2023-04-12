package com.example.musicandfilm.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class RecentItemRepository(private val recentItemDao: RecentItemDao) {

    val allRecentItems: LiveData<List<RecentHistory>> = recentItemDao.allRecentItems()
//get() = recentItemDao.allRecentItems()
    @WorkerThread
    suspend fun insertRecentItem(recentHistory: RecentHistory){
        recentItemDao.insertRecentItem(recentHistory)
    }

    @WorkerThread
    suspend fun deleteRecentItem(){
        recentItemDao.deleteRecentItem()
    }
}