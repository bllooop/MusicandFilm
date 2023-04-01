package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName


data class Photo (

  @SerializedName("album_id"   ) var albumId   : Int?             = null,
  @SerializedName("date"       ) var date      : Int?             = null,
  @SerializedName("id"         ) var id        : Int?             = null,
  @SerializedName("owner_id"   ) var ownerId   : Int?             = null,
  @SerializedName("access_key" ) var accessKey : String?          = null,
  @SerializedName("post_id"    ) var postId    : Int?             = null,
  @SerializedName("sizes"      ) var sizes     : ArrayList<Sizes> = arrayListOf(),
  @SerializedName("text"       ) var text      : String?          = null,
  @SerializedName("user_id"    ) var userId    : Int?             = null,
  @SerializedName("has_tags"   ) var hasTags   : Boolean?         = null

)