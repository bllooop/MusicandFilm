package com.example.musicandfilm.models.movies


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("overview")
    val overview: String
)