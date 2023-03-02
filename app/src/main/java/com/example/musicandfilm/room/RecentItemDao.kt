package com.example.musicandfilm.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentItemDao {
    @Query("SELECT * FROM recent_item_table ORDER BY  id ASC")
    fun allRecentItems(): Flow<RecentItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentItem(recentItem: RecentItem)

    suspend fun updateRecentItem(recentItem: RecentItem)

    suspend fun deleteRecentItem(recentItem: RecentItem)


}