package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue


data class Items (

  @SerializedName("attachments"     ) var attachments   : ArrayList<Attachments> = arrayListOf(),
  @SerializedName("date"            ) var date          : Int                  = 0,
  @SerializedName("id"              ) var id            : Int                   = 0,
  @SerializedName("owner_id"        ) var ownerId       : Int                   = 0,
  @SerializedName("text"            ) var text          : String                = ""
)