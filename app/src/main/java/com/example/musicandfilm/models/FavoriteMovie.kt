package com.example.musicandfilm.models


import com.google.firebase.auth.FirebaseUser
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FavoriteMovie(
    var userid: String = "",
    var id_movie: String = "",
    var title: String= "",
    var poster_path: String = "",
    var release_date: String= ""
    )
