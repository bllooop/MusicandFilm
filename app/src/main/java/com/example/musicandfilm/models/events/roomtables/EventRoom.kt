package com.example.musicandfilm.models.events.roomtables

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicandfilm.models.events.Dates
import com.example.musicandfilm.models.events.Images
import com.google.gson.annotations.SerializedName


@Entity(tableName = "event__table")
data class EventRoom(
    @PrimaryKey(autoGenerate = true)
    val id : String,

    @SerializedName("id")
    val event_id : String,

    @SerializedName("title")
    val title : String,
    @Embedded
    val images :ArrayList<Images>,
    @Embedded
    val dates  :Dates

)
