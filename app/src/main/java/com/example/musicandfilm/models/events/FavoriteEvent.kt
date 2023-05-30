package com.example.musicandfilm.models.events

data class FavoriteEvent (
    var userid: String = "",
    var id: String = "",
    var title: String= "",
    var image: String = "",
    var date: String= "",
    val unix: String = ""
)