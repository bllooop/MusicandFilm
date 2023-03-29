package com.example.musicandfilm.models.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse (

  @SerializedName("response")
  var response : List<Response>

): Parcelable {
  constructor() : this(mutableListOf())
}