package com.example.musicandfilm.models

import android.location.Location
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class EventDetail (
    @SerializedName("id")
    val id: Int,
    @SerializedName("dates")
    val dates: ArrayList<Dates>,
    @SerializedName("title")
    val title: String,
    @SerializedName("body_text")
    val bodyText: String,
    @SerializedName("location")
    val location: @RawValue Any?,
    @SerializedName("age_restriction" )
    val ageRestriction : String,
    @SerializedName("images")
    val images  :@RawValue Any?
)