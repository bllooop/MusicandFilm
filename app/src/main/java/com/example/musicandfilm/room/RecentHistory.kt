package com.example.musicandfilm.room

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "recent_item_table")
data class RecentHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var userid: String = "",
    var date: String = "",
    var type_id: String = "",
    var title: String = "",
    var image:String = "",
    var type:String = ""
)
