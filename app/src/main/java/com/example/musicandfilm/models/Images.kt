package com.example.musicandfilm.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images (
@SerializedName("image") var image  : String
) : Parcelable {
    constructor() : this("")
}