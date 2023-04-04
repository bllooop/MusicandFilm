package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName

data class Link (

    @SerializedName("url"   ) var url   : String?             = null,
    @SerializedName("photo" ) var photo : Photo?  = Photo()

)