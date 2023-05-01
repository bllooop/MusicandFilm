package com.example.musicandfilm.models.events

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicandfilm.models.events.Dates
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize

@Entity(tableName = "event_table")
@Parcelize
data class Event (
    @PrimaryKey(autoGenerate = false)
    val id : String,

    @SerializedName("title")
    val title : String,
    @Embedded
    val images :ArrayList<Images>,
    @Embedded
    val dates  : ArrayList<Dates>
) : Parcelable {
    constructor() : this("","", arrayListOf(), arrayListOf())
}