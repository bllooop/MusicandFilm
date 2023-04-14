package com.example.musicandfilm.models.events

import android.os.Parcelable
import com.example.musicandfilm.models.events.Dates
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Event (
    @SerializedName("id")
    val id : String,

    @SerializedName("title")
    val title : String,
    @SerializedName("images")
    val event_images :ArrayList<Images>,
    @SerializedName("dates")
    val dates  : ArrayList<Dates>

) : Parcelable {
    constructor() : this("", "", arrayListOf(),arrayListOf())
}