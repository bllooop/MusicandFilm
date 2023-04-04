package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName


data class Attachments (

  @SerializedName("type"  ) var type  : String? = null,
  @SerializedName("photo" ) var photo : Photo?  = Photo(),
  @SerializedName("video" ) var video : Video?  = Video(),
  @SerializedName("link" ) var link : Link?  = Link(),


  )