package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName


data class Image (

    @SerializedName("url"          ) var url         : String? = null,
    @SerializedName("width"        ) var width       : Int?    = null,
    @SerializedName("height"       ) var height      : Int?    = null,
    @SerializedName("with_padding" ) var withPadding : Int?    = null

)