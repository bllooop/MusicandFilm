package com.example.musicandfilm.models.events.roomtables

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class Images (
@SerializedName("image") var image  : String
)