package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName

data class CopyHistory (

    @SerializedName("type"        ) var type        : String?                = null,
    @SerializedName("attachments" ) var attachments : ArrayList<Attachments> = arrayListOf(),
    @SerializedName("date"        ) var date        : Int?                   = null,
    @SerializedName("from_id"     ) var fromId      : Int?                   = null,
    @SerializedName("id"          ) var id          : Int?                   = null,
    @SerializedName("owner_id"    ) var ownerId     : Int?                   = null,
    @SerializedName("post_type"   ) var postType    : String?                = null,
    @SerializedName("text"        ) var text        : String?                = null

)