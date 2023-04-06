package com.example.musicandfilm.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentItemDao {
    @Query("SELECT * FROM recent_item_table ORDER BY  id ASC")
    fun allRecentItems(): LiveData<List<RecentHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentItem(recentHistory: RecentHistory)

    @Update
    suspend fun updateRecentItem(recentHistory: RecentHistory)
    @Delete
    suspend fun deleteRecentItem(recentHistory: RecentHistory)


}