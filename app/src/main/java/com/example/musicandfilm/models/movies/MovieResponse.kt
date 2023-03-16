package com.example.musicandfilm.models.movies

import android.os.Parcelable
import com.example.musicandfilm.models.movies.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("results")
    val movies : List<Movie>

) : Parcelable {
    constructor() : this(mutableListOf())
}