package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName

data class Video (

    @SerializedName("access_key"       ) var accessKey     : String?          = null,
    @SerializedName("can_comment"      ) var canComment    : Int?             = null,
    @SerializedName("can_like"         ) var canLike       : Int?             = null,
    @SerializedName("can_repost"       ) var canRepost     : Int?             = null,
    @SerializedName("can_subscribe"    ) var canSubscribe  : Int?             = null,
    @SerializedName("can_add_to_faves" ) var canAddToFaves : Int?             = null,
    @SerializedName("can_add"          ) var canAdd        : Int?             = null,
    @SerializedName("comments"         ) var comments      : Int?             = null,
    @SerializedName("date"             ) var date          : Int?             = null,
    @SerializedName("description"      ) var description   : String?          = null,
    @SerializedName("duration"         ) var duration      : Int?             = null,
    @SerializedName("image"            ) var image         : ArrayList<Image> = arrayListOf(),
    @SerializedName("id"               ) var id            : Int?             = null,
    @SerializedName("owner_id"         ) var ownerId       : Int?             = null,
    @SerializedName("title"            ) var title         : String?          = null,
    @SerializedName("track_code"       ) var trackCode     : String?          = null,
    @SerializedName("type"             ) var type          : String?          = null,
    @SerializedName("views"            ) var views         : Int?             = null,
    @SerializedName("local_views"      ) var localViews    : Int?             = null,
    @SerializedName("platform"         ) var platform      : String?          = null,
    @SerializedName("can_dislike"      ) var canDislike    : Int?             = null

)