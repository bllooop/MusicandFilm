package com.example.musicandfilm.models.events

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventResponse(
    @SerializedName("results")
    val events : List<Event>
) : Parcelable {
    constructor() : this(mutableListOf())
}