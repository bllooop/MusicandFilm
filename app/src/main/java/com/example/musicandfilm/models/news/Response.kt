package com.example.musicandfilm.models.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Response (

  @SerializedName("count") var count    : String,
  @SerializedName("items") var items    : List<Items>,


): Parcelable {
  constructor() : this("", mutableListOf())
}