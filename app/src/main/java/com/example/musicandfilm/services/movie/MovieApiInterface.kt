package com.example.musicandfilm.services.movie

import com.example.musicandfilm.models.movies.MovieResponse
import com.example.musicandfilm.models.movies.MovieDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("/3/movie/popular?api_key=/=ru-RU")
    fun getMovieList(): Call<MovieResponse>
    @GET("/3/movie/{id}?api_key=/=ru-RU")
    fun getMovieDetails(@Path("id") movie_id: Int): Call<MovieDetails>

    @GET("/3/movie/")
    fun getMovieDetail(@Query("movie_id") movie_id: Int,
                       @Query("api_key") key: String): Call<MovieDetails>

}
