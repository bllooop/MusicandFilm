package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName


data class Items (

  @SerializedName("attachments"     ) var attachments   : ArrayList<Attachments> = arrayListOf(),
  @SerializedName("date"            ) var date          : Int?                   = null,
  @SerializedName("id"              ) var id            : Int?                   = null,
  @SerializedName("owner_id"        ) var ownerId       : Int?                   = null,
  @SerializedName("text"            ) var text          : String?                = null
)