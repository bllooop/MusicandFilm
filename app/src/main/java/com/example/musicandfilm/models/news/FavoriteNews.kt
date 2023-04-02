package com.example.musicandfilm.models.news

import com.google.gson.annotations.SerializedName

data class FavoriteNews (
    var userid: String = "",
    var date: String = "",
    var id: String = "",
    var text: String = "",
    var image:String = ""
){

}