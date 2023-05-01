package com.example.musicandfilm.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musicandfilm.models.events.Event
import com.example.musicandfilm.models.events.roomtables.EventRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentItemDao {
    @Query("SELECT * FROM recent_item_table ORDER BY  id ASC")
    fun allRecentItems(): LiveData<List<RecentHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentItem(recentHistory: RecentHistory)

    @Query("DELETE FROM recent_item_table where id NOT IN (SELECT id from recent_item_table ORDER BY id DESC LIMIT 3)")
    suspend fun deleteRecentItem()

   // @Query("SELECT * FROM event_table")
    //fun allEvents(): List<Event>

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
   // suspend fun insertEvents(eventRoom: List<Event>)

}