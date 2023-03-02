package com.example.musicandfilm.models

import android.os.Parcelable
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
    val event_images :@RawValue Any?,
    @SerializedName("dates"  )
    val dates  : ArrayList<Dates>

) : Parcelable {
    constructor() : this("", "","",arrayListOf())
}