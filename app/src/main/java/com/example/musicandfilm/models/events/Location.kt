package com.example.musicandfilm.models.events

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Location (
    @SerializedName("slug") var slug     : String,
    @SerializedName("name") var name     : String
    )