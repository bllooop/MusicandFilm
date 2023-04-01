package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName


data class Response (

  @SerializedName("count"    ) var count    : Int?                = null,
  @SerializedName("items"    ) var items    : ArrayList<Items>    = arrayListOf()
)