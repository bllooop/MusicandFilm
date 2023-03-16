package com.example.musicandfilm.models.events

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dates (

    @SerializedName("start" ) var start : String,
    @SerializedName("end"   ) var end   : String

) : Parcelable {
    constructor() : this("", "")
}