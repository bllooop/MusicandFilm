package com.example.musicandfilm.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class RecentItemRepository(private val recentItemDao: RecentItemDao) {

    val allRecentItems: Flow<RecentItem> = recentItemDao.allRecentItems()

    @WorkerThread
    suspend fun insertRecentItem(recentItem: RecentItem){
        recentItemDao.insertRecentItem(recentItem)
    }

    @WorkerThread
    suspend fun updateRecentItem(recentItem: RecentItem){
        recentItemDao.updateRecentItem(recentItem)
    }
}