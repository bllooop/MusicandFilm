package com.example.musicandfilm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicandfilm.models.events.Event

@Database(
    entities = [RecentHistory::class],
        //Event::class],
    version = 1
)
abstract class RecentItemDatabase :RoomDatabase() {
    abstract fun getRecentDao():RecentItemDao
    companion object{
        private var databaase: RecentItemDatabase?=null
        @Synchronized
        fun getInstance(context: Context):RecentItemDatabase{
            return if (databaase == null){
                databaase = Room.databaseBuilder(context,RecentItemDatabase::class.java,"db").build()
                databaase as RecentItemDatabase
            }else{
                databaase as RecentItemDatabase
            }
        }
    }
}