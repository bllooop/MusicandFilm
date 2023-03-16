package com.example.musicandfilm.models.events

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images (
@SerializedName("image") var image  : String
) : Parcelable {
    constructor() : this("")
}