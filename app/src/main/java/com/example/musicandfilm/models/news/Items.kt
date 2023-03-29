package com.example.musicandfilm.models.news

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Items (

  @SerializedName("is_pinned") var isPinned      : String,
  @SerializedName("marked_as_ads") var markedAsAds   : String,
  @SerializedName("short_text_rate") var shortTextRate : String,
  @SerializedName("hash") var hash          : String,
  @SerializedName("type") var type          : String,
  @SerializedName("date") var date          : String,
  @SerializedName("from_id") var fromId        : String,
  @SerializedName("id") var id            : String,
  @SerializedName("owner_id") var ownerId       : String,
  @SerializedName("post_type") var postType      : String,
  @SerializedName("text") var text          : String

): Parcelable {
  constructor() : this("", "","","","","","","","","","")
}